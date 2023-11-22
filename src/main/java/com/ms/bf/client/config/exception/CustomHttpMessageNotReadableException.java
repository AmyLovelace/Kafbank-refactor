package com.ms.bf.client.config.exception;

import com.ms.bf.client.config.ErrorCode;
import org.springframework.http.converter.HttpMessageNotReadableException;

public class CustomHttpMessageNotReadableException extends HttpMessageNotReadableException {


    public CustomHttpMessageNotReadableException(ErrorCode errorCode){
        super(String.valueOf(errorCode.getValue()));}

}
