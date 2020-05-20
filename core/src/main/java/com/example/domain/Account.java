package com.example.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 用户账户
 *
 * @author yichuan
 */
@Data
@Component
@ConfigurationProperties(prefix = "account")
public class Account {
    private String clientId;
    private String name;
    private String base64Secret;
    private int expiresSecond;
}
