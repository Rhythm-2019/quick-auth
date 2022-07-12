package org.mdnote.quickauth.requests;

/**
 * @author Rhythm-2019
 * @date 2022/7/12
 * @description HTTP 请求授权相关参数封装
 */
public class HttpAuthRequest implements AuthRequest {

    /**
     * url
     */
    private String url;
    /**
     * 签名 SHA256（argskv），kv之间使用 & 连接，k 和 v 使用 = 连接，英文字母全部为小写
     * kv（按先后顺序）：url + app-id + app-secret + timestamp
     */
    private String token;
    /**
     * 时间戳 (s)
     */
    private long timestamp;
    /**
     * 请求编号
     */
    private String requestId;
    /**
     * 应用id
     */
    private String appId;

    public HttpAuthRequest(String url, String token, long timestamp, String requestId, String appId) {
        this.url = url;
        this.token = token;
        this.timestamp = timestamp;
        this.requestId = requestId;
        this.appId = appId;
    }

    public String getUrl() {
        return this.url;
    }

    @Override
    public String getToken() {
        return this.token;
    }

    @Override
    public long getTimestamp() {
        return this.timestamp;
    }

    @Override
    public String getAppId() {
        return this.appId;
    }

    @Override
    public String getRequestId() {
        return this.requestId;
    }
}
