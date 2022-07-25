package com.peri.fashion.userInfo.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.peri.fashion.userInfo.model.pojo.SysUser;
import com.peri.fashion.userInfo.model.req.UserLoginReq;
import com.peri.fashion.userInfo.model.req.UserRegisterReq;

import java.util.function.Consumer;

/**
 * 系统 - 用户表 Service
 *
 * @author Elliot
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 用户注册
     *
     * @return 注册结果
     */
    Integer registerUser(UserRegisterReq req, JSONObject log, Consumer<JSONObject> consumerLog);

    /**
     * 再次获取邮箱验证码
     *
     * @param mail 邮箱
     * @return 校验结果
     */
    Integer getVerifyEmailCode(String mail);

    /**
     * 邮箱验证码校验
     *
     * @param mail 邮箱
     * @param code 验证码
     * @return 校验结果
     */
    Integer verifyCode(String mail, Integer code);

    /**
     * 用户登陆
     *
     * @return 登陆结果
     */
    Integer doLogin(UserLoginReq req);
}
