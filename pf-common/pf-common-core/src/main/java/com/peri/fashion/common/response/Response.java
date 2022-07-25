package com.peri.fashion.common.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author Elliot
 */
@Slf4j
@Component
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Response {

    @Resource
    private MessageSource messageSource;

    private static Response response;

    @PostConstruct
    public void init() {
        response = this;
        log.info("Response:通用响应体加载完毕~");
    }

    /**
     * 成功数据
     */
    public static <T> ResponseInfo<T> data(T data) {
        return returnResult(ResponseEnum.SUCCESS.getCode(), response.messageSource.getMessage(ResponseEnum.SUCCESS.getMessage(), null, LocaleContextHolder.getLocale()), data);
    }

    public static <T> ResponseInfo<T> data(String message, T data) {
        return returnResult(ResponseEnum.SUCCESS.getCode(), response.messageSource.getMessage(message, null, LocaleContextHolder.getLocale()), data);
    }

    public static <T> ResponseInfo<T> data(ResponseEnum responseEnum, T data) {
        return returnResult(responseEnum.getCode(), response.messageSource.getMessage(responseEnum.getMessage(), null, LocaleContextHolder.getLocale()), data);
    }

    /**
     * 请求成功
     */
    public static <T> ResponseInfo<T> success() {
        return returnResult(ResponseEnum.SUCCESS.getCode(), response.messageSource.getMessage(ResponseEnum.SUCCESS.getMessage(), null, LocaleContextHolder.getLocale()), null);
    }

    public static <T> ResponseInfo<T> success(String message) {
        return returnResult(ResponseEnum.SUCCESS.getCode(), response.messageSource.getMessage(message, null, LocaleContextHolder.getLocale()), null);
    }

    public static <T> ResponseInfo<T> success(ResponseEnum responseEnum) {
        return returnResult(responseEnum.getCode(), response.messageSource.getMessage(responseEnum.getMessage(), null, LocaleContextHolder.getLocale()), null);
    }

    public static <T> ResponseInfo<T> success(T data) {
        return returnResult(ResponseEnum.SUCCESS.getCode(), response.messageSource.getMessage(ResponseEnum.SUCCESS.getMessage(), null, LocaleContextHolder.getLocale()), data);
    }

    /**
     * 请求失败
     */
    public static <T> ResponseInfo<T> fail() {
        return returnResult(ResponseEnum.FAILURE.getCode(), response.messageSource.getMessage(ResponseEnum.FAILURE.getMessage(), null, LocaleContextHolder.getLocale()), null);
    }

    public static <T> ResponseInfo<T> fail(String message) {
        return returnResult(ResponseEnum.FAILURE.getCode(), response.messageSource.getMessage(message, null, LocaleContextHolder.getLocale()), null);
    }

    public static <T> ResponseInfo<T> fail(ResponseEnum responseEnum) {
        return returnResult(responseEnum.getCode(), response.messageSource.getMessage(responseEnum.getMessage(), null, LocaleContextHolder.getLocale()), null);
    }

    /**
     * 未找到
     */
    public static <T> ResponseInfo<T> notFound() {
        return returnResult(ResponseEnum.NOTFOUND.getCode(), response.messageSource.getMessage(ResponseEnum.NOTFOUND.getMessage(), null, LocaleContextHolder.getLocale()), null);
    }

    public static <T> ResponseInfo<T> notFound(String message) {
        return returnResult(ResponseEnum.NOTFOUND.getCode(), response.messageSource.getMessage(message, null, LocaleContextHolder.getLocale()), null);
    }

    public static <T> ResponseInfo<T> notFound(ResponseEnum responseEnum) {
        return returnResult(responseEnum.getCode(), response.messageSource.getMessage(responseEnum.getMessage(), null, LocaleContextHolder.getLocale()), null);
    }

    /**
     * 自定义
     */
    public static <T> ResponseInfo<T> custom(int code, String message, String... args) {
        return returnResult(code, response.messageSource.getMessage(message, args, LocaleContextHolder.getLocale()), null);
    }

    public static <T> ResponseInfo<T> custom(int code, String message, T data, String... args) {
        return returnResult(code, response.messageSource.getMessage(message, args, LocaleContextHolder.getLocale()), data);
    }

    private static <T> ResponseInfo<T> returnResult(int code, String message, T data) {
        ResponseInfo<T> responseInfo = new ResponseInfo<>();
        responseInfo.setStatusCode(code);
        responseInfo.setMessage(message);
        responseInfo.setData(data);
        return responseInfo;
    }

}