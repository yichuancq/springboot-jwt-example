package com.example.handler;

import com.example.common.response.Result;
import com.example.common.response.ResultCode;
import com.example.common.response.exception.DataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author yichuan
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理自定义异常
     */
    @ExceptionHandler(DataException.class)
    public Result handleException(DataException e) {
        // 打印异常信息
        log.error("异常信息:{}", e.getMessage());
        return new Result(e.getResultCode());
    }

    /**
     * 参数错误异常
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public Result handleException(Exception e) {
        Result handlerResult = new Result();
        StringBuilder errorMsg = new StringBuilder();
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) e;
            BindingResult result = validException.getBindingResult();
            if (result.hasErrors()) {
                List<ObjectError> errors = result.getAllErrors();
                errors.forEach(p -> {
                    FieldError fieldError = (FieldError) p;
                    errorMsg.append(fieldError.getDefaultMessage()).append(",");
                    log.error("请求参数错误：{}", fieldError.getObjectName());
                    log.error("fieldError:{}", fieldError.getField());
                    log.error("errorMessage:{}", fieldError.getDefaultMessage());
                });
            }
        } else if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            if (bindException.hasErrors()) {
                log.error("请求参数错误: {}", bindException.getAllErrors());
            }
        }
        handlerResult.setCode(ResultCode.PARAM_IS_INVALID.code());
        handlerResult.setMessage(errorMsg.toString());
        return handlerResult;
    }

    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Exception.class)
    public Result handleOtherException(Exception e) {
        //打印异常堆栈信息
        //e.printStackTrace();
        // 打印异常信息
        log.error("不可知的异常:{}", e.getMessage());
        return new Result(ResultCode.SYSTEM_INNER_ERROR);
    }


}
