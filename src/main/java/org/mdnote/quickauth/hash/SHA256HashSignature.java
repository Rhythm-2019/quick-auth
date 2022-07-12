package org.mdnote.quickauth.hash;

import org.mdnote.quickauth.utils.HashUtil;

import java.util.Map;

/**
 * @author Rhythm-2019
 * @date 2022/7/12
 * @description
 */
public class SHA256HashSignature implements HashSignature {

    @Override
    public String hash(Map<String, String> param) {
        StringBuilder stringBuilder = new StringBuilder();
        param.forEach((k, v) -> {
            stringBuilder.append(k.toLowerCase())
                    .append("=")
                    .append(v.toLowerCase())
                    .append("&");
        });
        return HashUtil.sha256(stringBuilder.substring(0, stringBuilder.length() - 1));
    }
}
