package com.example.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.annotation.JwtIgnore;
import com.example.common.response.Result;
import com.example.domain.Account;
import com.example.domain.dto.AccountDto;
import com.example.utils.JwtTokenUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author yichuan
 */
@Slf4j
@RestController
public class ValidateController {

    @Autowired
    private Account account;


    /**
     * 先登录，后请求其他接口请求时把key,value放入header里面
     * key=token
     * value=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiYWRtaW4iLCJ1c2VySWQiOiJjM2I4Mzc3NC0xYmFmLTRiMDgtOGU2MC0xODI1MGRiYTJhNTEiLCJzdWIiOiJ0ZXN0IiwiaXNzIjoiMTIzNDU2IiwiaWF0IjoxNTg5OTU0OTkxLCJhdWQiOiJyZXN0YXBpdXNlciIsImV4cCI6MTU4OTk1NTE2NCwibmJmIjoxNTg5OTU0OTkxfQ.gARDjQKKyoQDxQUE_AWd2vkqDGhXXKrTE5gCeoPc3yE
     *
     * @param accountDto
     * @param response
     * @return
     */
    @JwtIgnore
    @PostMapping("/login")
    @ApiOperation(value = "/login", notes = "用户登录验证")
    public Result login(@RequestBody AccountDto accountDto, HttpServletResponse response) {
        if (accountDto == null || StringUtils.isEmpty(accountDto.getUserName())) {
            return null;
        }
        // 这里模拟测试, 默认登录成功，返回用户ID和角色信息
        String userId = UUID.randomUUID().toString();
        String role = "admin";
        // 创建token
        log.info("accountDto:{}", accountDto.toString());
        String token = JwtTokenUtil.createJWT(userId, accountDto.getUserName(), role, account);
        log.info("登录成功,token:{}", token);
        // 将token放在响应头
        response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, JwtTokenUtil.TOKEN_PREFIX + token);
        // 将token响应给客户端
        JSONObject result = new JSONObject();
        result.put("token", token);
        return Result.SUCCESS(result);
    }

    @GetMapping("/getMessage")
    public Result getMessage() {
        log.info("你已通过验证");
        return Result.SUCCESS();
    }
}
