package com.example.annotation;


import java.lang.annotation.*;

/**
 * JWT验证忽略注解
 *
 * @author yichuan
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PassToken {
    boolean required() default true;
}

