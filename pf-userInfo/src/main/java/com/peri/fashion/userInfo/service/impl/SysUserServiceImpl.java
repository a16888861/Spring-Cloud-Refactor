package com.peri.fashion.userInfo.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peri.fashion.common.constant.CommonConstants;
import com.peri.fashion.common.exception.InterfaceException;
import com.peri.fashion.common.util.Md5Utils;
import com.peri.fashion.userInfo.enums.JudgeUserInfoEnum;
import com.peri.fashion.userInfo.enums.UserStatusEnum;
import com.peri.fashion.userInfo.mapper.SysUserMapper;
import com.peri.fashion.userInfo.model.pojo.SysUser;
import com.peri.fashion.userInfo.model.req.UserLoginReq;
import com.peri.fashion.userInfo.model.req.UserRegisterReq;
import com.peri.fashion.userInfo.service.SysUserService;
import com.peri.fashion.userInfo.utils.Mail163Util;
import com.peri.fashion.userInfo.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.function.Consumer;

/**
 * 系统 - 用户表 ServiceImpl
 *
 * @author Elliot
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private RedisUtil redisUtil;

    /**
     * 校验用户状态
     *
     * @param selected 用户信息
     * @return 校验结果
     */
    private Integer checkUserStatus(SysUser selected, Integer resultType) {
        // 用户不存在
        if (ObjectUtil.isNull(selected)) {
            resultType = JudgeUserInfoEnum.USER_VALIDATED_USER_IS_NULL.getType();
            return resultType;
        }
        // 审核已通过
        if (selected.getStatus().equals(UserStatusEnum.NORMAL.getStatus())) {
            resultType = JudgeUserInfoEnum.USER_VALIDATED_USER_NOT_NULL.getType();
            return resultType;
        }
        // 已锁定
        if (selected.getStatus().equals(UserStatusEnum.LOCK.getStatus())) {
            resultType = JudgeUserInfoEnum.USER_VALIDATED_USER_IS_LOCKED.getType();
            return resultType;
        }
        return resultType;
    }

    /**
     * 设置Redis验证码并发送验证码邮件
     *
     * @param mail 邮件
     * @return 结果
     */
    private Boolean setRedisEmailCodeAndSendEmailMessage(String mail) {
        // Redis存储用户验证码 五分钟内有效
        int verificationCode = (int) ((Math.random() * 9 + 1) * 1000);
        boolean verify = redisUtil.set("verifyCode-" + mail, verificationCode, 60 * 5);
        if (!verify) {
            return Boolean.FALSE;
        }
        // 发送邮件验证码
        Mail163Util.sendMail(CommonConstants.USER_EMAIL_REGISTER_TITLE_FORMAT,
                CommonConstants.USER_EMAIL_REGISTER_CONTENT_FORMAT.replace("$0", String.valueOf(verificationCode)),
                mail);
        return Boolean.TRUE;
    }

    /**
     * 用户注册
     *
     * @return 注册结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer registerUser(UserRegisterReq req, JSONObject logs, Consumer<JSONObject> consumerLog) {
        // 验证两次输入的密码
        if (!req.getPassword().equals(req.getVerifyPassword())) {
            logs.put("info", "registerUser " + JudgeUserInfoEnum.USER_VALIDATED_MATCH_PASSWORD.getDesc());
            return JudgeUserInfoEnum.USER_VALIDATED_MATCH_PASSWORD.getType();
        }
        // 验证邮箱在库中是否存在 存在则提示已经注册
        SysUser selected = getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getMail, req.getMail()));
        if (ObjectUtil.isNotNull(selected)) {
            logs.put("info", "registerUser " + JudgeUserInfoEnum.USER_VALIDATED_REQUEST_EMAIL_NOT_NULL.getDesc());
            return JudgeUserInfoEnum.USER_VALIDATED_REQUEST_EMAIL_NOT_NULL.getType();
        }
        // 不存在则添加数据
        SysUser sysUser = new SysUser();
        sysUser.setName(req.getName()).setPassword(Md5Utils.md5Hex(req.getPassword())).setMail(req.getMail()).setYear(String.valueOf(LocalDateTime.now().getYear()));
        if (!save(sysUser)) {
            logs.put("info", "registerUser " + JudgeUserInfoEnum.USER_VALIDATED_USER_NOT_NULL.getDesc());
            return JudgeUserInfoEnum.USER_VALIDATED_USER_NOT_NULL.getType();
        }
        // 设置缓存 发送验证码
        Boolean result = setRedisEmailCodeAndSendEmailMessage(req.getMail());
        if (!result) {
            logs.put("info", "registerUser redisCacheFailure");
            return JudgeUserInfoEnum.FAILURE.getType();
        }
        logs.put("info", "registerUser success~");
        consumerLog.accept(logs);
        return JudgeUserInfoEnum.SUCCESS.getType();
    }

    /**
     * 再次获取邮箱验证码
     *
     * @param mail 邮箱
     * @return 校验结果
     */
    @Override
    public Integer getVerifyEmailCode(String mail) {
        // 1-Redis的验证码不为空 取出并重新发送
        Integer verifyCode = (Integer) redisUtil.get("verifyCode-" + mail);
        if (ObjectUtil.isNotNull(verifyCode)) {
            Mail163Util.sendMail(CommonConstants.USER_EMAIL_REGISTER_TITLE_FORMAT,
                    CommonConstants.USER_EMAIL_REGISTER_CONTENT_FORMAT.replace("$0", String.valueOf(verifyCode)),
                    mail);
            return JudgeUserInfoEnum.SUCCESS.getType();
        }
        // 2-校验用户信息
        Integer resultType = JudgeUserInfoEnum.SUCCESS.getType();
        resultType = checkUserStatus(getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getMail, mail)), resultType);
        if (!resultType.equals(JudgeUserInfoEnum.SUCCESS.getType())) {
            return resultType;
        }
        // 3-设置缓存 发送验证码
        Boolean result = setRedisEmailCodeAndSendEmailMessage(mail);
        if (!result) {
            return JudgeUserInfoEnum.FAILURE.getType();
        }
        return resultType;
    }

    /**
     * 邮箱验证码校验
     *
     * @param mail 邮箱
     * @param code 验证码
     * @return 校验结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer verifyCode(String mail, Integer code) {
        // 1-校验验证码
        Integer verifyCode = (Integer) redisUtil.get("verifyCode-" + mail);
        if (ObjectUtil.isNull(verifyCode)) {
            return JudgeUserInfoEnum.USER_VALIDATED_EMAIL_CODE_EXPIRED.getType();
        }
        if (!code.equals(verifyCode)) {
            return JudgeUserInfoEnum.USER_VALIDATED_REQUEST_EMAIL_CODE_ERROR.getType();
        }
        // 2-校验用户信息
        SysUser selected = getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getMail, mail));
        Integer resultType = JudgeUserInfoEnum.SUCCESS.getType();
        resultType = checkUserStatus(selected, resultType);
        if (!resultType.equals(JudgeUserInfoEnum.SUCCESS.getType())) {
            return resultType;
        }
        // 3-更新用户状态 删除Redis缓存的验证码
        try {
            selected.setStatus(1);
            redisUtil.del("verifyCode-" + mail);
            updateById(selected);
        } catch (InterfaceException e) {
            throw new InterfaceException(JudgeUserInfoEnum.EMAIL_CODE_EXCEPTION.getDesc());
        }
        return resultType;
    }

    /**
     * 用户登陆
     *
     * @return 登陆结果
     */
    @Override
    public Integer doLogin(UserLoginReq req) {
        SysUser selected = getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getMail, req.getMail()));
        // 用户不存在
        if (ObjectUtil.isNull(selected)) {
            return JudgeUserInfoEnum.USER_VALIDATED_USER_IS_NULL.getType();
        }
        // 待审批
        if (selected.getStatus().equals(UserStatusEnum.APPROVAL.getStatus())) {
            return JudgeUserInfoEnum.USER_VALIDATED_USER_IS_PENDING.getType();
        }
        // 已锁定
        if (selected.getStatus().equals(UserStatusEnum.LOCK.getStatus())) {
            return JudgeUserInfoEnum.USER_VALIDATED_USER_IS_LOCKED.getType();
        }
        // 密码不一致
        if (!Md5Utils.md5Hex(req.getPassword()).equals(selected.getPassword())) {
            return JudgeUserInfoEnum.USER_VALIDATED_REQUEST_PASSWORD_ERR.getType();
        }
        // 都通过执行登陆动作
        StpUtil.login(selected.getId());
        return JudgeUserInfoEnum.SUCCESS.getType();
    }
}
