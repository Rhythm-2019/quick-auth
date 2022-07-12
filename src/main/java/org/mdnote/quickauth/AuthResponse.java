package org.mdnote.quickauth;

/**
 * @author Rhythm-2019
 * @date 2022/7/12
 * @description 授权结果封装
 */
public class AuthResponse {

    /**
     * 状态
     */
    private State state;
    /**
     * 错误信息
     */
    private String message;

    public AuthResponse(State state) {
        this.state = state;
    }

    public AuthResponse(State state, String message) {
        this(state);
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isSuccess() {
        return this.state == State.SUCCESS;
    }

    public boolean isExpired() {
        return this.state == State.EXPIRED;
    }

    public boolean isFailure() {
        return this.state == State.FAILURE;
    }

    public enum State {
        /**
         * 成功
         */
        SUCCESS(0),
        /**
         * 过期
         */
        EXPIRED(1),
        /**
         * 失败
         */
        FAILURE(2);

        private int code;

        State(int code) {
            this.code = code;
        }

    }
}
