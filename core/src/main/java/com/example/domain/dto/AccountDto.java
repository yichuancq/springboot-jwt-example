package com.example.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用户登录Dto
 *
 * @author yichuan
 */
@Getter
@Setter
@ToString
public class AccountDto implements Serializable {
    private String userName;
    private String password;
    private String userId;
}
