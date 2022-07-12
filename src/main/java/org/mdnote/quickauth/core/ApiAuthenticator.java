package org.mdnote.quickauth.core;

import org.mdnote.quickauth.AuthResponse;
import org.mdnote.quickauth.requests.AuthRequest;

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
