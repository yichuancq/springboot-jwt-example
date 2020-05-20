package com.example.annotation;


import java.lang.annotation.*;

/**
 * JWT验证忽略注解
 * @author yichuan
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JwtIgnore {
}

