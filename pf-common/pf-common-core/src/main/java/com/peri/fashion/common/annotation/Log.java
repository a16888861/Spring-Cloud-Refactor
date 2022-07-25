package com.peri.fashion.common.annotation;

import org.springframework.core.annotation.Order;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义Log注解
 * 加上注解则记录操作人操作方法
 *
 * @author Elliot
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Order(1)
public @interface Log {
    String value() default "";
}