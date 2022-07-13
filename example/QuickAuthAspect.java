package demo.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.mdnote.quickauth.AuthResponse;
import org.mdnote.quickauth.core.ApiAuthenticator;
import org.mdnote.quickauth.requests.HttpAuthRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @author Rhythm-2019
 * @date 2022/7/12
 * @description AOP
 */
@Aspect
@Component
public class QuickAuthAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuickAuthAspect.class);

    @Resource
    private ApiAuthenticator<HttpAuthRequest> apiAuthenticator;

    @Pointcut("@annotation(demo.demo.aop.QuickAuth)")
    public void annotationPointCut(){}

    @Around("annotationPointCut()")
    public Object beforeAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            LOGGER.error("failed to get request");
            return null;
        }

        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) requestAttributes).getResponse();

        String ts = request.getHeader("X-CA-Timestamp");
        String appId = request.getHeader("App-Id");
        String token = request.getHeader("Token");

        if (!StringUtils.hasText(ts) || !StringUtils.hasText(appId) || !StringUtils.hasText(token)) {
            sendResponse(request, response, 403, "missing auth args");
            return null;
        }
        System.out.println(ts);
        if (!ts.chars().allMatch( Character::isDigit)) {
            sendResponse(request, response, 403, "bad auth args");
            return null;
        }

        AuthResponse authResponse = apiAuthenticator.auth(new HttpAuthRequest(request.getRequestURL().toString(), token, Long.parseLong(ts), "", appId));
        if (authResponse.isExpired()) {
            // response expired
            sendResponse(request, response, 403, "token expired");
            return null;
        } else if (authResponse.isFailure()) {
            // response failure
            sendResponse(request, response, 403, "error token");
            return null;
        }
        LOGGER.info("auth success in {}, addr {}", request.getRequestURL(), request.getRemoteAddr());

        // execute method
        return proceedingJoinPoint.proceed();
    }

    private void sendResponse(HttpServletRequest request, HttpServletResponse response, int stateCode, String message) {
        Optional.of(response).ifPresent((response1 -> {
            response.setStatus(stateCode);
            try {
                response.getWriter().write(message);
//                response.flushBuffer();
            } catch (IOException e) {
                LOGGER.error("send response to {} failed", request.getRemoteAddr());
            }
        }));
    }

}
