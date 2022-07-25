package com.peri.fashion.common.exception;

import com.peri.fashion.common.response.Response;
import com.peri.fashion.common.response.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常拦截
 *
 * @author Elliot
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * 自定义请求非法异常拦截
     *
     * @param requestIllegalException 参数异常
     * @return 返回结果
     */
    @ExceptionHandler(RequestIllegalException.class)
    public ResponseInfo<Object> handleParameterException(RequestIllegalException requestIllegalException) {
        return Response.custom(ExceptionEnum.REQUEST_ILLEGAL_EXCEPTION.getCode(), requestIllegalException.getMessage(), null);
    }

    /**
     * 自定义参数异常拦截
     *
     * @param parameterException 参数异常
     * @return 返回结果
     */
    @ExceptionHandler(ParameterException.class)
    public ResponseInfo<Object> handleParameterException(ParameterException parameterException) {
        return Response.custom(ExceptionEnum.PARAMETER_EXCEPTION.getCode(), parameterException.getMessage(), null);
    }

    /**
     * 自定义网络异常拦截
     *
     * @param netException 网络异常
     * @return 返回结果
     */
    @ExceptionHandler(NetException.class)
    public ResponseInfo<Object> handleNetException(NetException netException) {
        return Response.custom(ExceptionEnum.NET_EXCEPTION.getCode(), netException.getMessage(), null);
    }

    /**
     * 自定义邮件服务异常拦截
     *
     * @param mailException 网络异常
     * @return 返回结果
     */
    @ExceptionHandler(MailException.class)
    public ResponseInfo<Object> handleMailException(MailException mailException) {
        return Response.custom(ExceptionEnum.MAIL_EXCEPTION.getCode(), mailException.getMessage(), null);
    }

    /**
     * 自定义接口服务异常拦截
     *
     * @param mailException 网络异常
     * @return 返回结果
     */
    @ExceptionHandler(InterfaceException.class)
    public ResponseInfo<Object> handleMailException(InterfaceException mailException) {
        return Response.custom(ExceptionEnum.INTERFACE_EXCEPTION.getCode(), mailException.getMessage(), null);
    }

}
