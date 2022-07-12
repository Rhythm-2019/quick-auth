package org.mdnote.quickauth.exception;

/**
 * @author Rhythm-2019
 * @date 2022/7/12
 * @description 惨呼异常
 */
public class ArgException extends RuntimeException {
    private String message;

    public ArgException(String message, Exception e) {
        super(e);
        this.message = message;
    }

    public ArgException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
