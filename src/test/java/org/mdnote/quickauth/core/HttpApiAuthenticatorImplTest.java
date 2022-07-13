package org.mdnote.quickauth.core;


import org.junit.Test;
import org.mdnote.quickauth.AuthResponse;
import org.mdnote.quickauth.hash.SHA256HashSignature;
import org.mdnote.quickauth.requests.HttpAuthRequest;
import org.mdnote.quickauth.storge.CredentialStorage;
import org.mdnote.quickauth.storge.PropertiesFileCredentialStorage;
import org.mdnote.quickauth.utils.HashUtil;

import java.net.URISyntaxException;
import java.util.HashMap;


/**
 * @author Rhythm-2019
 * @date 2022/7/12
 * @description 授权测试用例
 */
public class HttpApiAuthenticatorImplTest {

    @Test
    public void testNormal() {
        String filepath = this.getClass().getClassLoader().getResource("secret.dat").getFile();
        CredentialStorage localCredentialStorage = new PropertiesFileCredentialStorage(filepath);

        // make a token
        String url = "http://localhost";
        String appId = "rhythm-2019";
        String appSecret = "123456";
        long ts = System.currentTimeMillis() / 1000;

        String token = HashUtil.sha256(url + appId + appSecret + ts);
        HttpAuthRequest httpUrlApiRequest = new HttpAuthRequest(url, token, ts, "", appId);

        AuthResponse authResponse = new HttpApiAuthenticator(localCredentialStorage, new SHA256HashSignature())
                .auth(httpUrlApiRequest);

        assert authResponse.isSuccess();
    }

    @Test
    public void testExpired() throws URISyntaxException {
        HashMap<String, String> args = new HashMap<>();
        String filepath = this.getClass().getClassLoader().getResource("secret.dat").getFile();
        CredentialStorage localCredentialStorage = new PropertiesFileCredentialStorage(filepath);

        // make a token
        String url = "http://localhost";
        String appId = "rhythm-2019";
        String appSecret = "123456";
        long ts = System.currentTimeMillis() / 1000;

        String token = HashUtil.sha256(url + appId + appSecret + (ts - 61));
        HttpAuthRequest httpUrlApiRequest = new HttpAuthRequest(url, token, ts - 61, "", appId);

        AuthResponse authResponse = new HttpApiAuthenticator(localCredentialStorage, new SHA256HashSignature())
                .auth(httpUrlApiRequest);

        assert authResponse.isExpired();
    }

    @Test
    public void testFailure() {
        HashMap<String, String> args = new HashMap<>();
        String filepath = this.getClass().getClassLoader().getResource("secret.dat").getFile();
        CredentialStorage localCredentialStorage = new PropertiesFileCredentialStorage(filepath);

        // make a token
        String url = "http://localhost";
        String appId = "rhythm-2019";
        String appSecret = "123456";
        long ts = System.currentTimeMillis() / 1000;

        String token = HashUtil.sha256(url + appId + appSecret + ts);
        HttpAuthRequest httpUrlApiRequest = new HttpAuthRequest(url, token, ts - 50, "", appId);

        AuthResponse authResponse = new HttpApiAuthenticator(localCredentialStorage, new SHA256HashSignature())
                .auth(httpUrlApiRequest);

        assert authResponse.isFailure();
    }

}