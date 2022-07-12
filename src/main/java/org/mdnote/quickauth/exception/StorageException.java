package org.mdnote.quickauth.exception;

/**
 * @author Rhythm-2019
 * @date 2022/7/12
 * @description 存储异常
 */
public class StorageException extends RuntimeException {
    private String message;

    public StorageException(String message, Exception e) {
        super(e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
