package com.peri.fashion.userInfo.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.peri.fashion.common.annotation.IgnoreSecurity;
import com.peri.fashion.common.annotation.Log;
import com.peri.fashion.common.constant.CommonConstants;
import com.peri.fashion.common.context.UserContextUtil;
import com.peri.fashion.common.dto.SysUserDTO;
import com.peri.fashion.common.response.Response;
import com.peri.fashion.common.response.ResponseInfo;
import com.peri.fashion.userInfo.enums.JudgeUserInfoEnum;
import com.peri.fashion.userInfo.model.req.UserLoginReq;
import com.peri.fashion.userInfo.model.req.UserRegisterReq;
import com.peri.fashion.userInfo.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 用户信息相关接口
 *
 * @author Elliot
 */
@Api(value = "用户信息", tags = "用户信息相关接口")
@ApiSupport(order = 0, author = "Elliot")
@Slf4j
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 用户注册
     *
     * @return 结果
     */
    @ApiOperation(value = "用户注册(邮箱)", produces = "application/json",
            notes = "用户注册调用的接口")
    @ApiOperationSupport(author = "Elliot")
    @PostMapping("/register")
    public ResponseInfo<Object> register(@Validated @RequestBody UserRegisterReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.fail(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        JSONObject logs = new JSONObject();
        Integer result = sysUserService.registerUser(req, logs,(resultLog)-> log.info(JSONObject.toJSONString(resultLog)));
        Map<Integer, String> resultMap = JudgeUserInfoEnum.getMap();
        return Response.custom(result, resultMap.get(result));
    }

    /**
     * 再次获取邮箱验证码
     *
     * @param mail 邮箱
     * @return 校验结果
     */
    @IgnoreSecurity
    @ApiOperation(value = "再次获取邮箱验证码", notes = "用于提交了注册申请后验证码过期的情况")
    @ApiOperationSupport(author = "Elliot", order = 1)
    @GetMapping("/getVerifyEmailCode/{mail}")
    public ResponseInfo<Object> getVerifyEmailCode(@Email(message = CommonConstants.USER_VALIDATED_REQUEST_EMAIL_FORMAT) @PathVariable("mail") String mail) {

        Integer verifyCode = sysUserService.getVerifyEmailCode(mail);
        Map<Integer, String> resultMap = JudgeUserInfoEnum.getMap();
        return Response.custom(verifyCode, resultMap.get(verifyCode));
    }

    /**
     * 邮箱验证码校验
     *
     * @param mail 邮箱
     * @param code 验证码
     * @return 校验结果
     */
    @IgnoreSecurity
    @ApiOperation(value = "邮箱验证码校验", notes = "调用用户注册接口后调用校验接口")
    @ApiOperationSupport(author = "Elliot", order = 2)
    @GetMapping("/verifyCode/{mail}/{code}")
    public ResponseInfo<Object> verifyCode(@Email(message = CommonConstants.USER_VALIDATED_REQUEST_EMAIL_FORMAT) @PathVariable("mail") String mail,
                                           @NotNull(message = CommonConstants.USER_VALIDATED_REQUEST_EMAIL_CODE_VERIFY) @PathVariable("code") Integer code) {
        Integer verifyCode = sysUserService.verifyCode(mail, code);
        Map<Integer, String> resultMap = JudgeUserInfoEnum.getMap();
        return Response.custom(verifyCode, resultMap.get(verifyCode));
    }

    /**
     * 用户登陆
     */
    @ApiOperation(value = "用户登陆", produces = "application/json",
            notes = "用户登陆调用的接口<br>" +
                    "登陆成功后添加token到请求头<br>" +
                    "键值对按照返回的内容添加原样即可")
    @ApiOperationSupport(author = "Elliot", order = 3)
    @PostMapping("/doLogin")
    public ResponseInfo<Object> doLogin(@Validated @RequestBody UserLoginReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.fail(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Integer loginResult = sysUserService.doLogin(req);
        if (!loginResult.equals(JudgeUserInfoEnum.SUCCESS.getType())) {
            Map<Integer, String> resultMap = JudgeUserInfoEnum.getMap();
            return Response.custom(loginResult, resultMap.get(loginResult));
        }
        // 每次登陆返回的token都不一致 一个账号支持多端登陆
        String tokenValue = StpUtil.getTokenInfo().getTokenValue();
        JSONObject resultObject = new JSONObject();
        resultObject.put(CommonConstants.AUTHORIZATION, tokenValue);
        return Response.success(resultObject);
    }

    /**
     * 获取用户登陆信息
     */
    @Log("获取用户登陆信息")
    @ApiOperation(value = "获取用户登陆信息",
            notes = "用户登陆成功后<br>" +
                    "可调用此接口获取用户相关信息")
    @ApiOperationSupport(author = "Elliot", order = 4)
    @GetMapping("/getUserInfo")
    public ResponseInfo<SysUserDTO> getUserInfo() {
        return Response.success(UserContextUtil.getUserInfo());
    }
}
