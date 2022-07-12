package org.mdnote.quickauth.core;


import org.junit.Test;
import org.mdnote.quickauth.AuthResponse;
import org.mdnote.quickauth.requests.HttpAuthRequest;
import org.mdnote.quickauth.storge.CredentialStorage;
import org.mdnote.quickauth.storge.UrlPropertiesCredentialStorageImpl;
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
    public void testNormal() throws URISyntaxException {
        HashMap<String, String> args = new HashMap<>();
        String filepath = this.getClass().getClassLoader().getResource("secret.dat").toURI().toString();
        args.put("url", filepath);
        CredentialStorage localCredentialStorage = new UrlPropertiesCredentialStorageImpl(args);

        // make a token
        String url = "http://localhost";
        String appId = "rhythm-2019";
        long ts = System.currentTimeMillis() / 1000;

        String payload = "url=" + url + "&app-id=" + appId + "&app-secret=123456&timestamp=" + ts;
        String token = HashUtil.sha256(payload);
        HttpAuthRequest httpUrlApiRequest = new HttpAuthRequest(url, token, ts, "", appId);

        AuthResponse authResponse = new HttpApiAuthenticatorImpl(localCredentialStorage)
                .auth(httpUrlApiRequest);

        assert authResponse.isSuccess();
    }

    @Test
    public void testExpired() throws URISyntaxException {
        HashMap<String, String> args = new HashMap<>();
        String filepath = this.getClass().getClassLoader().getResource("secret.dat").toURI().toString();
        args.put("url", filepath);
        CredentialStorage localCredentialStorage = new UrlPropertiesCredentialStorageImpl(args);

        // make a token
        String url = "http://localhost";
        String appId = "rhythm-2019";
        long ts = System.currentTimeMillis() / 1000;

        String payload = "url=" + url + "&app-id=" + appId + "&app-secret=123456&timestamp=" + (ts - 62);
        String token = HashUtil.sha256(payload);
        HttpAuthRequest httpUrlApiRequest = new HttpAuthRequest(url, token, ts - 62, "", appId);

        AuthResponse authResponse = new HttpApiAuthenticatorImpl(localCredentialStorage)
                .auth(httpUrlApiRequest);

        assert authResponse.isExpired();
    }

    @Test
    public void testFailure() throws URISyntaxException {
        HashMap<String, String> args = new HashMap<>();
        String filepath = this.getClass().getClassLoader().getResource("secret.dat").toURI().toString();
        args.put("url", filepath);
        CredentialStorage localCredentialStorage = new UrlPropertiesCredentialStorageImpl(args);

        // make a token
        String url = "http://localhost";
        String appId = "rhythm-2019";
        long ts = System.currentTimeMillis() / 1000;

        String payload = "url=" + url + "&app-id=" + appId + "&app-secret=123456&timestamp=" + ts;
        String token = HashUtil.sha256(payload);
        HttpAuthRequest httpUrlApiRequest = new HttpAuthRequest(url, token, ts - 50, "", appId);

        AuthResponse authResponse = new HttpApiAuthenticatorImpl(localCredentialStorage)
                .auth(httpUrlApiRequest);

        assert authResponse.isFailure();
    }

}