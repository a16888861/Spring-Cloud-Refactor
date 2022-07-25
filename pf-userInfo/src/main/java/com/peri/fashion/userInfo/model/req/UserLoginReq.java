package com.peri.fashion.userInfo.model.req;

import com.peri.fashion.common.constant.CommonConstants;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户登陆 Req
 *
 * @author Elliot
 */
@Data
@ToString
@Accessors(chain = true)
public class UserLoginReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 邮箱
     */
    @NotBlank(message = CommonConstants.USER_VALIDATED_REQUEST_EMAIL)
    @Email(message = CommonConstants.USER_VALIDATED_REQUEST_EMAIL_FORMAT)
    private String mail;

    /**
     * 密码
     */
    @NotBlank(message = CommonConstants.USER_VALIDATED_REQUEST_PASSWORD)
    private String password;

}
