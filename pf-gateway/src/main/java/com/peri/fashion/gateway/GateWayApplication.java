package com.peri.fashion.gateway;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * @author Elliot
 */
@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
public class GateWayApplication {

    @SneakyThrows
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(GateWayApplication.class, args);
        Environment environment = context.getBean(Environment.class);
        log.info("GateWay Start Success ~");
        log.info("The Localhost Api Doc Address Is:\thttp://localhost:" + environment.getProperty("server.port") + "/doc.html");
    }

    /**
     * 用于解决跨域问题
     */
    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);

        log.info("CorsFilter配置完成～");
        return new CorsWebFilter(source);
    }
}