package com.peri.fashion.common.exception;

/**
 * 接口异常
 *
 * @author Elliot
 */
public class InterfaceException extends RuntimeException {

    public InterfaceException(String message) {
        super(message);
    }

    public InterfaceException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterfaceException(Throwable cause) {
        super(cause);
    }

    public InterfaceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
