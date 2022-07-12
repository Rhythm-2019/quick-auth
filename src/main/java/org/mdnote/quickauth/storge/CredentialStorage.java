package org.mdnote.quickauth.storge;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Map;

/**
 * @author Rhythm-2019
 * @date 2022/7/12
 * @description 密钥存储接口
 */
public interface CredentialStorage {
    /**
     * 根据 appId 获取密钥
     * @param appId appId
     * @return 密钥
     */
    String getAppSecretByAppId(String appId);
}
