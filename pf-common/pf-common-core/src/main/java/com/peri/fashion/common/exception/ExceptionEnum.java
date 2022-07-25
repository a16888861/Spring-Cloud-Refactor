package com.peri.fashion.common.exception;

/**
 * 异常枚举
 *
 * @author Elliot
 */

public enum ExceptionEnum {

    /**
     * 请求非法
     */
    REQUEST_ILLEGAL_EXCEPTION(503, "request.illegal.exception.response"),

    /**
     * 参数异常
     */
    PARAMETER_EXCEPTION(503, "parameter.exception.response"),

    /**
     * 网络通信异常
     */
    NET_EXCEPTION(503, "net.exception.response"),

    /**
     * 邮件服务异常
     */
    MAIL_EXCEPTION(503, "mail.exception.response"),

    /**
     * 接口异常
     */
    INTERFACE_EXCEPTION(503, "interface.exception.response");

    private final int code;

    private final String message;

    ExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
