package org.mdnote.quickauth.signature;

import org.mdnote.quickauth.requests.HttpAuthRequest;
import org.mdnote.quickauth.utils.HashUtil;

/**
 * @author Rhythm-2019
 * @date 2022/7/12
 * @description SHA256 签名算法
 */
public class DefaultHttpSignature implements HashSignature<HttpAuthRequest> {

    @Override
    public String hash(HttpAuthRequest authRequest) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(authRequest.getUrl())
                .append(authRequest.getAppId())
                .append(authRequest.getAppSecret())
                .append(authRequest.getTimestamp());

        return HashUtil.sha256(stringBuilder.toString());
    }
}
