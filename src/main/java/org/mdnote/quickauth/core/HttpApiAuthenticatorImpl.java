package org.mdnote.quickauth.core;

import org.mdnote.quickauth.AuthResponse;
import org.mdnote.quickauth.AuthToken;
import org.mdnote.quickauth.exception.ArgException;
import org.mdnote.quickauth.requests.AuthRequest;
import org.mdnote.quickauth.requests.HttpAuthRequest;
import org.mdnote.quickauth.storge.CredentialStorage;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Rhythm-2019
 * @date 2022/7/12
 * @description 基于 HTTP 授权
 */
public class HttpApiAuthenticatorImpl implements ApiAuthenticator {

    private CredentialStorage credentialStorage;

    public HttpApiAuthenticatorImpl(CredentialStorage credentialStorage) {
        this.credentialStorage = credentialStorage;
    }

    /**
     * 授权
     * @param authRequest 授权参数
     *                    注意入参需要是 @click HttpAuthRequest 类型
     * @return 是否成功
     */
    @Override
    public AuthResponse auth(AuthRequest authRequest) {
        if (authRequest == null) {
            throw new ArgException("arg should not be null");
        }
        if (authRequest.getAppId() == null || "".equals(authRequest.getAppId())) {
            throw new ArgException("api id can not be empty");
        }
        if (authRequest.getTimestamp() == 0) {
            throw new ArgException("timestamp can not be empty");
        }

        // TODO 根据request-id过滤重放请求

        if (!(authRequest instanceof HttpAuthRequest)) {
            throw new ArgException("expected arg type is HttpUrlApiRequest");
        }
        HttpAuthRequest httpUrlApiRequest = (HttpAuthRequest) authRequest;

        String appSecret = this.credentialStorage.getAppSecretByAppId(httpUrlApiRequest.getAppId());
        if (appSecret == null) {
            return new AuthResponse(AuthResponse.State.FAILURE, "app secret no found");
        }

        Map<String, String> arg = new LinkedHashMap<>();
        arg.put("url", httpUrlApiRequest.getUrl());
        arg.put("app-id", httpUrlApiRequest.getAppId());
        arg.put("app-secret", appSecret);
        arg.put("timestamp", String.valueOf(httpUrlApiRequest.getTimestamp()));
        AuthToken authToken = AuthToken.create(httpUrlApiRequest.getTimestamp(), arg);

        if (!authToken.match(httpUrlApiRequest.getToken())) {
            return new AuthResponse(AuthResponse.State.FAILURE);
        }
        if (authToken.isExpired()) {
            return new AuthResponse(AuthResponse.State.EXPIRED);
        }

        return new AuthResponse(AuthResponse.State.SUCCESS);
    }
}
