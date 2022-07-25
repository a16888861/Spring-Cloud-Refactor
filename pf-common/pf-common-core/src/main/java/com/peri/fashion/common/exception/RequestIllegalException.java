package com.peri.fashion.common.exception;

/**
 * 请求非法异常
 *
 * @author Elliot
 */
public class RequestIllegalException extends RuntimeException {

    public RequestIllegalException(String message) {
        super(message);
    }

    public RequestIllegalException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestIllegalException(Throwable cause) {
        super(cause);
    }

    public RequestIllegalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
