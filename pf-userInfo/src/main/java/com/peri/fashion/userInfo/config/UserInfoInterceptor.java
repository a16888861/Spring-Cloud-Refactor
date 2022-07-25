package com.peri.fashion.userInfo.config;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.peri.fashion.common.annotation.IgnoreSecurity;
import com.peri.fashion.common.constant.CommonConstants;
import com.peri.fashion.common.context.UserContextUtil;
import com.peri.fashion.common.dto.SysUserDTO;
import com.peri.fashion.common.exception.ExceptionEnum;
import com.peri.fashion.common.exception.ParameterException;
import com.peri.fashion.common.exception.RequestIllegalException;
import com.peri.fashion.common.util.BeanUtil;
import com.peri.fashion.userInfo.model.pojo.SysUser;
import com.peri.fashion.userInfo.service.SysUserService;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 用户信息拦截器 - 配合视图拦截器使用
 *
 * @author Elliot
 */
@Slf4j(topic = "userInfoInterceptor")
@Component
public class UserInfoInterceptor implements HandlerInterceptor {

    @Resource
    private SysUserService sysUserService;

    private final ThreadLocal<Long> THREAD = new ThreadLocal<>();

    /**
     * preHandle 请求前拦截
     * 调用时间：Controller方法处理之前
     * 执行顺序：链式Intercepter情况下，Intercepter按照声明的顺序一个接一个执行
     * 若返回false，则中断执行，注意：不会进入afterCompletion
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理
     * @return 结果
     */
    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        THREAD.set(System.currentTimeMillis());
        /* 配合放行注解使用 - 如果加了该注解则放行接口 */
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            if (method.isAnnotationPresent(IgnoreSecurity.class)) {
                log.info(request.getServletPath() + "为忽略校验接口");
                return Boolean.TRUE;
            }
        }
        /* 获取请求头授权信息(也就是token和刷新token) */
        String authorization = request.getHeader(CommonConstants.AUTHORIZATION);
        if (StringUtil.isNullOrEmpty(authorization)) {
            log.info("请求头Token为空!!!");
            throw new ParameterException(ExceptionEnum.PARAMETER_EXCEPTION.getMessage());
        }
        /* 校验请求是否非法 */
        String result = (String) StpUtil.getLoginIdByToken(authorization);
        if (ObjectUtil.isNull(result)) {
            log.info("非法请求头Token信息:{},Response:{}", authorization, ExceptionEnum.REQUEST_ILLEGAL_EXCEPTION.getMessage());
            throw new RequestIllegalException(ExceptionEnum.REQUEST_ILLEGAL_EXCEPTION.getMessage());
        }
        Integer loginIdByToken = Integer.parseInt(result);
        /* 请求正常则查询用户相关信息存储在全局线程中 */
        SysUser serviceById = sysUserService.getById(loginIdByToken);
        serviceById.setPassword(null);
        SysUserDTO sysUserDTO = BeanUtil.copyProperties(serviceById, SysUserDTO.class);
        UserContextUtil.setUserInfo(sysUserDTO);
        return Boolean.TRUE;
    }

    /**
     * postHandle
     * 调用前提：preHandle返回true
     * 调用时间：Controller方法处理完之后，DispatcherServlet进行视图的渲染之前，也就是说在这个方法中你可以对ModelAndView进行操作
     * 执行顺序：链式Intercepter情况下，Intercepter按照声明的顺序倒着执行。
     * 备注：postHandle虽然post打头，但post、get方法都能处理
     */
    @Override
    public void postHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, ModelAndView modelAndView) throws Exception {
//        log.info("postHandle未做任何处理");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * afterCompletion
     * 调用前提：preHandle返回true
     * 调用时间：DispatcherServlet进行视图的渲染之后
     * 多用于清理资源
     */
    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) throws Exception {
        /* 统计方法执行耗时 */
        long millis = System.currentTimeMillis();
        log.info(request.getServletPath() + "耗时：" + (millis - THREAD.get()) + "ms");
        THREAD.remove();
        /* 每次请求完成后清理存储的用户线程 */
        UserContextUtil.remove();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}