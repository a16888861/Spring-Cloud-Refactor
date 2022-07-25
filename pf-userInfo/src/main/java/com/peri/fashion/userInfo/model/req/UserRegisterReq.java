package com.peri.fashion.userInfo.model.req;

import com.peri.fashion.common.constant.CommonConstants;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户注册 Req
 *
 * @author Elliot
 */
@Data
@ToString
@Accessors(chain = true)
public class UserRegisterReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @NotBlank(message = CommonConstants.USER_VALIDATED_REQUEST_USERNAME)
    private String name;

    /**
     * 密码
     */
    @NotBlank(message = CommonConstants.USER_VALIDATED_REQUEST_PASSWORD)
    private String password;

    /**
     * 校验密码
     */
    @NotBlank(message = CommonConstants.USER_VALIDATED_REQUEST_PASSWORD)
    private String verifyPassword;

    /**
     * 邮箱
     */
    @NotBlank(message = CommonConstants.USER_VALIDATED_REQUEST_EMAIL)
    @Email(message = CommonConstants.USER_VALIDATED_REQUEST_EMAIL_FORMAT)
    private String mail;

}
