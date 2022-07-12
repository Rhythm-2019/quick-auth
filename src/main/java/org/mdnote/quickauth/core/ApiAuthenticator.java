package org.mdnote.quickauth.core;

import org.mdnote.quickauth.requests.AuthRequest;
import org.mdnote.quickauth.AuthResponse;

/**
 * @author Rhythm-2019
 * @date 2022/7/12
 * @description 授权接口
 */
public interface ApiAuthenticator {
    /**
     * 授权
     * @param authRequest 授权参数
     * @return 授权接口
     */
    AuthResponse auth(AuthRequest authRequest);
}
