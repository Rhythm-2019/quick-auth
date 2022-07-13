package org.mdnote.quickauth.hash;

import org.mdnote.quickauth.requests.AuthRequest;

import java.util.Map;

/**
 * @author Rhythm-2019
 * @date 2022/7/12
 * @description
 */
public interface HashSignature<T extends AuthRequest> {
    /**
     * 计算密钥
     *
     * @param authRequest 用户传递的参数
     * @return 密钥
     */
    String hash(T authRequest);
}
