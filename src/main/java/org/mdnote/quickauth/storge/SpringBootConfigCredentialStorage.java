package org.mdnote.quickauth.storge;

import java.util.Collections;
import java.util.Map;

/**
 * @author Rhythm-2019
 * @date 2022/7/12
 * @description 配置文件
 */
public class SpringBootConfigCredentialStorage implements CredentialStorage{

    private Map<Object, Object> storage;

    public SpringBootConfigCredentialStorage(Map<Object, Object> appIdSecretMap) {
        this.storage = Collections.synchronizedMap(appIdSecretMap);
    }

    @Override
    public String getAppSecretByAppId(String appId) {
        return String.valueOf(this.storage.get(appId));
    }
}
