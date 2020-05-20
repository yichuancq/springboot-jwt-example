package com.example.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户账户
 * @author yichuan
 */
@Getter
@Setter
@ToString
public class Account {
    private String clientId;
    private String name;
    private String base64Secret;
    private int expiresSecond;
}
