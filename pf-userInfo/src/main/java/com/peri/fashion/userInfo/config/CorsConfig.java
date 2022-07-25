package com.peri.fashion.userInfo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于解决跨域问题
 *
 * @author Elliot
 */
@Slf4j
@Configuration
public class CorsConfig {

    /**
     * 沿用Spring框架的CorsFilter
     * 也可以重写方法addCorsMappings(WebMvcConfigurer中)
     *
     * @return 配置结果
     */
    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(Boolean.TRUE);
        /* 允许访问的方法名,GET POST等 可以设置全部 config.addAllowedMethod("*")); 也可以进行单独的指定 */
        List<String> addAllowedMethods = new ArrayList<>();
        for (HttpMethod method : HttpMethod.values()) {
            addAllowedMethods.add(method.name());
        }
        config.setAllowedMethods(addAllowedMethods);
        /* 允许服务端访问的客户端请求头 */
        config.addAllowedHeader("*");
        /* 允许访问的客户端域名(这里自行添加) */
        List<String> allowedOriginPatterns = new ArrayList<>();
        allowedOriginPatterns.add("demo");
        config.setAllowedOriginPatterns(allowedOriginPatterns);

        /* 对接口配置跨域设置 */
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);

        log.info("解决跨域问题配置注入完成～");
        return new CorsWebFilter(source);
    }

}