package com.peri.fashion.userInfo.config;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * swagger配置类
 *
 * @author Elliot
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Value("${knife4j.enable}")
    public boolean knife4jEnable;

    private final OpenApiExtensionResolver openApiExtensionResolver;

    @Autowired
    public Swagger2Config(OpenApiExtensionResolver openApiExtensionResolver) {
        this.openApiExtensionResolver = openApiExtensionResolver;
    }

    /**
     * 分割符
     */
    private static final String SPLIT_SYMBOL = ";";

    @Bean(value = "用户模块")
    public Docket api1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(knife4jEnable)
                /*分组名称(要和网关的路由id一致)*/
                .groupName("用户模块")
                .select()
                /*采用包扫描的方式来确定要显示的接口*/
//                .apis(RequestHandlerSelectors.basePackage("com.peri.fashion.userInfo.controller"))
                /*采用包含注解的方式来确定要显示的接口(两种方式：根据类注释和根据方法注释，看情况选择)*/
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                /*自写多包扫描方法(方法过时)*/
//                .apis(basePackage("com.lucky.kali.business.controller"))
                /*扫描全部*/
                .paths(PathSelectors.any())
                /*扫描指定*/
//                .paths(PathSelectors.regex("/index/*"))
                .build()
                .extensions(openApiExtensionResolver.buildExtensions("用户模块"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("UserInfo RESTFUL APIs(Swagger-3.0.3)")
                .description("用户模块API文档")
                .contact(new Contact("Elliot", "https://xstrojan.top/about/", "a12888821@gmail.com"))
                .version("1.0")
                .termsOfServiceUrl("https://github.com/a16888861/Spring-Cloud")
                .build();
    }

    /**
     * 配置Swagger多包
     *
     * @param basePackage 包路径
     * @return Predicate(Predicate接口主要用来判断一个参数是否符合要求)
     */
    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return (input) -> (Boolean) declaringClass(input).map(handlerPackage(basePackage)).orElse(true);
    }

    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(SPLIT_SYMBOL)) {
                boolean isMatch = ClassUtils.getPackageName(input).startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.ofNullable(input.declaringClass());
    }
}