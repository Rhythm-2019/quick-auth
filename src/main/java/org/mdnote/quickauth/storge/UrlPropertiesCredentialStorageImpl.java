package org.mdnote.quickauth.storge;

import org.mdnote.quickauth.exception.StorageException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

/**
 * @author Rhythm-2019
 * @date 2022/7/12
 * @description URL（本地、网络）文件春初，properties 格式
 */
public class UrlPropertiesCredentialStorageImpl implements CredentialStorage {

    private static final String FILE_LOCAL_KEY = "url";

    private Map<Object, Object> appIdSecretMap;

    public UrlPropertiesCredentialStorageImpl(Map<String, String> params) {
        try (FileReader fileReader = new FileReader(new File(new URI(params.get(FILE_LOCAL_KEY))));) {
            Properties properties = new Properties();
            properties.load(fileReader);
            this.appIdSecretMap = Collections.synchronizedMap(properties);
            ;
        } catch (FileNotFoundException e) {
            throw new StorageException("file no found", e);
        } catch (IOException e) {
            throw new StorageException("error in load app secret", e);
        } catch (URISyntaxException e) {
            throw new StorageException("url bad format", e);
        }
    }

    @Override
    public String getAppSecretByAppId(String appId) {
        return String.valueOf(this.appIdSecretMap.get(appId));
    }
}
