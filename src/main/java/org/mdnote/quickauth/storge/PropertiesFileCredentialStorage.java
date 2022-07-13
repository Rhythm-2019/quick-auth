package org.mdnote.quickauth.storge;

import org.mdnote.quickauth.exception.ArgException;
import org.mdnote.quickauth.exception.StorageException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

/**
 * @author Rhythm-2019
 * @date 2022/7/12
 * @description URL（本地、网络）文件春初，properties 格式
 */
public class PropertiesFileCredentialStorage implements CredentialStorage {

    private Map<Object, Object> appIdSecretMap;

    public PropertiesFileCredentialStorage(String propertyFilePath) {
        File propertyFile = null;
        if (propertyFilePath.startsWith("classpath")) {
            try {
                propertyFile = ResourceUtils.getFile(propertyFilePath);
            } catch (IOException e) {
                throw new ArgException("file no found", e);
            }
        } else {
            propertyFile = new File(propertyFilePath);
        }
        try (FileReader fileReader = new FileReader(propertyFile);) {
            Properties appIdSecretStorage = new Properties();
            appIdSecretStorage.load(fileReader);
            this.appIdSecretMap = Collections.synchronizedMap(appIdSecretStorage);
            ;
        } catch (FileNotFoundException e) {
            throw new StorageException("file no found", e);
        } catch (IOException e) {
            throw new StorageException("error in load app secret", e);
        }
    }

    @Override
    public String getAppSecretByAppId(String appId) {
        return String.valueOf(this.appIdSecretMap.get(appId));
    }
}
