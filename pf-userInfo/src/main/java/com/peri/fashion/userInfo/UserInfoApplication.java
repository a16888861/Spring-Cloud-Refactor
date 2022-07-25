package com.peri.fashion.userInfo;

import cn.dev33.satoken.SaManager;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.core.env.Environment;

/**
 * 用户模块启动器
 *
 * @author Elliot
 */
@Slf4j
@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients(basePackages = "com.peri.fashion.userInfo")
@ComponentScans(value = {
        // 公共 - 请求响应,异常配置,日志切面
        @ComponentScan("com.peri.fashion.common.response"),
        @ComponentScan("com.peri.fashion.common.exception"),
        @ComponentScan("com.peri.fashion.common.aspect"),
        // 模块专属 - 用户模块配置
        @ComponentScan("com.peri.fashion.userInfo.utils"),
        @ComponentScan("com.peri.fashion.userInfo.config"),
        @ComponentScan("com.peri.fashion.userInfo.service"),
        @ComponentScan("com.peri.fashion.userInfo.controller"),
})
public class UserInfoApplication {

    @SneakyThrows
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(UserInfoApplication.class, args);
        Environment environment = context.getBean(Environment.class);
        log.info("UserInfo Start Success ~");
        log.info("Sa-Token info:" + SaManager.getConfig());
        log.info("The Localhost Api Doc Address Is:\thttp://localhost:" + environment.getProperty("server.port") + "/doc.html");
    }

}
