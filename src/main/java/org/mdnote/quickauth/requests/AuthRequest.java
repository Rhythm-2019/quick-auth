package org.mdnote.quickauth.requests;

/**
 * @author Rhythm-2019
 * @date 2022/7/12
 * @description 用户请求授权相关参数封装
 */
public interface AuthRequest {
    /**
     * 获取 token
     * @return token
     */
    String getToken();

    /**
     * 获取时间戳
     * @return 时间戳 (s)
     */
    long getTimestamp();

    /**
     * 获取 appId
     * @return appId
     */
    String getAppId();

    /**
     * 获取 reqId
     * @return 请求编号
     */
    String getRequestId();
}
