package org.mdnote.quickauth;

import org.mdnote.quickauth.utils.HashUtil;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author Rhythm-2019
 * @date 2022/7/12
 * @description Token 封装
 */
public class AuthToken {

    private static final long DEFAULT_EXPIRED_INTERVAL_SECONDS = 60;

    /**
     * 密钥
     */
    private String token;
    /**
     * 创建时的时间戳
     */
    private LocalDateTime timestamp;

    private AuthToken() {
    }

    private AuthToken(long timestamp, String token) {
        this.token = token;
        this.timestamp = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone.getDefault().toZoneId());
    }

    public static AuthToken create(long timestamp, Map<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder();
        params.forEach((k, v) -> {
            stringBuilder.append(k.toLowerCase())
                    .append("=")
                    .append(v.toLowerCase())
                    .append("&");
        });
        return new AuthToken(timestamp, HashUtil.sha256(stringBuilder.substring(0, stringBuilder.length() - 1)));
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.timestamp.plus(DEFAULT_EXPIRED_INTERVAL_SECONDS, ChronoUnit.SECONDS));
    }

    public boolean match(String token) {
        return this.token.equals(token);
    }

    public String getToken() {
        return token;
    }
}
