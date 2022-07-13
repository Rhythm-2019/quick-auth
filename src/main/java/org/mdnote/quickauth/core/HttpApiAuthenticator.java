package org.mdnote.quickauth.core;

import org.mdnote.quickauth.AuthResponse;
import org.mdnote.quickauth.AuthToken;
import org.mdnote.quickauth.exception.ArgException;
import org.mdnote.quickauth.hash.HashSignature;
import org.mdnote.quickauth.requests.HttpAuthRequest;
import org.mdnote.quickauth.storge.CredentialStorage;

/**
 * @author Rhythm-2019
 * @date 2022/7/12
 * @description 基于 HTTP 授权
 */
public class HttpApiAuthenticator implements ApiAuthenticator<HttpAuthRequest> {

    private CredentialStorage credentialStorage;

    private HashSignature<HttpAuthRequest> hashSignature;

    public HttpApiAuthenticator(CredentialStorage credentialStorage, HashSignature<HttpAuthRequest> hashSignature) {
        this.credentialStorage = credentialStorage;
        this.hashSignature = hashSignature;
    }

    /**
     * 授权
     *
     * @param authRequest 授权参数
     *                    注意入参需要是 @click HttpAuthRequest 类型
     * @return 是否成功
     */
    @Override
    public AuthResponse auth(HttpAuthRequest authRequest) {
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

        String appSecret = this.credentialStorage.getAppSecretByAppId(authRequest.getAppId());
        if (appSecret == null) {
            return new AuthResponse(AuthResponse.State.FAILURE, "app secret no found");
        }
        authRequest.setAppSecret(appSecret);

        AuthToken authToken = AuthToken.create(authRequest.getTimestamp(), authRequest, this.hashSignature);

        if (!authToken.match(authRequest.getToken())) {
            return new AuthResponse(AuthResponse.State.FAILURE);
        }
        if (authToken.isExpired()) {
            return new AuthResponse(AuthResponse.State.EXPIRED);
        }

        return new AuthResponse(AuthResponse.State.SUCCESS);
    }
}
