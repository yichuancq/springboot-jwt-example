package com.example.common.response.exception;

import com.example.common.response.ResultCode;

import java.text.MessageFormat;

/**
 * 自定义数据异常
 */
public class DataException extends RuntimeException {

    //错误代码
    private ResultCode resultCode;

    public DataException(ResultCode resultCode) {
        super(resultCode.message());
        this.resultCode = resultCode;
    }

    public DataException(ResultCode resultCode, Object... args) {
        super(resultCode.message());
        String message = MessageFormat.format(resultCode.message(), args);
        resultCode.setMessage(message);
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

}
