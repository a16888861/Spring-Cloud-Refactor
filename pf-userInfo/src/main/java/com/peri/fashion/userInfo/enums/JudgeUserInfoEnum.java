package com.peri.fashion.userInfo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户信息判断枚举类型
 *
 * @author Elliot
 */
@Getter
@AllArgsConstructor
public enum JudgeUserInfoEnum {

    /**
     * 成功
     */
    SUCCESS(HttpStatus.SC_OK, "common.response.success"),
    /**
     * 失败
     */
    FAILURE(HttpStatus.SC_EXPECTATION_FAILED, "common.response.failure"),
    /**
     * 两次输入的密码不一致
     */
    USER_VALIDATED_MATCH_PASSWORD(2, "user.validated.request.match.password"),
    /**
     * 邮箱已存在
     */
    USER_VALIDATED_REQUEST_EMAIL_NOT_NULL(3, "user.validated.request.email.notNull"),
    /**
     * 用户已存在
     */
    USER_VALIDATED_USER_NOT_NULL(4, "user.validated.user.notNull"),
    /**
     * 用户不存在
     */
    USER_VALIDATED_USER_IS_NULL(5, "user.validated.user.isNull"),
    /**
     * 用户已锁定
     */
    USER_VALIDATED_USER_IS_LOCKED(6, "user.validated.user.locked"),
    /**
     * 验证码已过期
     */
    USER_VALIDATED_EMAIL_CODE_EXPIRED(7, "user.validated.email.expired"),
    /**
     * 验证码校验服务异常
     */
    EMAIL_CODE_EXCEPTION(8, "user.validated.email.code.exception"),
    /**
     * 邮箱验证码错误
     */
    USER_VALIDATED_REQUEST_EMAIL_CODE_ERROR(9, "user.validated.request.email.codeError"),
    /**
     * 用户待审批
     */
    USER_VALIDATED_USER_IS_PENDING(10, "user.validated.user.pending"),
    /**
     * 密码错误
     */
    USER_VALIDATED_REQUEST_PASSWORD_ERR(11, "user.validated.request.password.err"),
    ;

    /**
     * code类型
     */
    private final Integer type;
    /**
     * 返回描述
     */
    private final String desc;


    private static final Map<Integer, String> MAP = new HashMap<>();

    static {
        for (JudgeUserInfoEnum judgeUserInfoEnum : JudgeUserInfoEnum.values()) {
            MAP.put(judgeUserInfoEnum.getType(), judgeUserInfoEnum.getDesc());
        }
    }

    public static Map<Integer, String> getMap() {
        return MAP;
    }

}
