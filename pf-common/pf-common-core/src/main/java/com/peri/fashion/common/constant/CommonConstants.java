package com.peri.fashion.common.constant;

import java.time.format.DateTimeFormatter;

/**
 * 公共常量类
 *
 * @author Elliot
 */
public class CommonConstants {

    /*----------------------------------------全局线程相关----------------------------------------*/
    /**
     * 全局线程存放登陆用户key
     */
    public static final String USER_INFO_KEY =  "login_user";

    /*----------------------------------------请求头相关----------------------------------------*/
    /**
     * 请求头Token存放key
     */
    public static final String AUTHORIZATION =  "Authorization";

    /*----------------------------------------线程相关----------------------------------------*/
    /*异步线程配置*/
    /**
     * 获取到服务器的cpu内核
     */
    public static final int CPUS = Runtime.getRuntime().availableProcessors();
    /**
     * 核心线程数(线程的数量设置为CPU 核数 +1，会实现最优的利用率)
     */
    public static final Integer ASYNC_EXECUTOR_THREAD_CORE_POOL_SIZE = CPUS + 1;
    /**
     * 最大线程数
     */
    public static final Integer ASYNC_EXECUTOR_THREAD_MAX_POOL_SIZE = 1000;
    /**
     * 队列大小
     */
    public static final Integer ASYNC_EXECUTOR_THREAD_QUEUE_CAPACITY = 99999;
    /**
     * 线程池中的线程的名称前缀
     */
    public static final String ASYNC_EXECUTOR_THREAD_NAME_PREFIX = "async-service-";

    /*----------------------------------------日期相关----------------------------------------*/
    /**
     * 日期格式(yyyy:MM:dd HH:mm:ss)
     */
    public static final DateTimeFormatter COMMON_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
    /**
     * 日期格式(yyyy年:MM月:dd日-HH时:mm分:ss秒)
     */
    public static final DateTimeFormatter CHINA_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy年:MM月:dd日-HH时:mm分:ss秒");

    /*----------------------------------------验证码格式相关----------------------------------------*/
    /**
     * 注册验证码标题格式
     */
    public static final String USER_EMAIL_REGISTER_TITLE_FORMAT = "阿森Blog - 注册验证码";
    /**
     * 注册验证码内容格式
     */
    public static final String USER_EMAIL_REGISTER_CONTENT_FORMAT = "您的验证码为：$0 请妥善保管(五分钟内有效)";

    /*----------------------------------------校验相关----------------------------------------*/
    /**
     * 用户名不允许为空
     */
    public static final String USER_VALIDATED_REQUEST_USERNAME = "user.validated.request.username";
    /**
     * 密码不允许为空
     */
    public static final String USER_VALIDATED_REQUEST_PASSWORD = "user.validated.request.password";
    /**
     * 邮箱不允许为空
     */
    public static final String USER_VALIDATED_REQUEST_EMAIL = "user.validated.request.email";
    /**
     * 邮箱验证码不允许为空
     */
    public static final String USER_VALIDATED_REQUEST_EMAIL_CODE_VERIFY = "user.validated.request.email.codeVerify";
    /**
     * 邮箱格式不正确
     */
    public static final String USER_VALIDATED_REQUEST_EMAIL_FORMAT = "user.validated.request.emailFormatErr";
}
