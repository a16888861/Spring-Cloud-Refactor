package com.peri.fashion.userInfo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态枚举类型
 *
 * @author Elliot
 */
@Getter
@AllArgsConstructor
public enum UserStatusEnum {

    /**
     * 审批
     */
    APPROVAL(0, "审批"),
    /**
     * 正常
     */
    NORMAL(1, "正常"),
    /**
     * 锁定
     */
    LOCK(2, "锁定");

    /**
     * 状态
     */
    private final Integer status;

    /**
     * 描述
     */
    private final String desc;

}
