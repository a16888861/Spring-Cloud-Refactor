package com.peri.fashion.userInfo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * WebMvc配置
 *
 * @author Elliot
 */
@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private UserInfoInterceptor userInfoInterceptor;

    /**
     * 注册用户信息拦截器
     *
     * @param registry 注册用户
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 初始化拦截放行的接口
        List<String> excludePathList = new ArrayList<>(
                Arrays.asList("/", "/doc.html", "/error",
                        "/favicon.ico", "/webjars/**", "/swagger-resources",
                        "/v2/**")
        );
        // 自定义添加的忽略校验的接口
        excludePathList.add("/user/register");
        excludePathList.add("/user/getVerifyEmailCode");
        excludePathList.add("/user/verifyCode");
        excludePathList.add("/user/doLogin");
        /* 自定义拦截器 */
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(userInfoInterceptor);
        interceptorRegistration.addPathPatterns("/**");
        interceptorRegistration.excludePathPatterns(excludePathList);
        log.info("拦截器放行列表为:{}", excludePathList);
    }

}