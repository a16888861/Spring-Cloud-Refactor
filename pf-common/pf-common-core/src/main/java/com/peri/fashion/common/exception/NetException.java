package com.peri.fashion.common.exception;

/**
 * 网络通信异常
 *
 * @author Elliot
 */
public class NetException extends RuntimeException {

    public NetException(String message) {
        super(message);
    }

    public NetException(String message, Throwable cause) {
        super(message, cause);
    }

    public NetException(Throwable cause) {
        super(cause);
    }

    public NetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
