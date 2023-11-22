package com.ms.bf.card.config.exception;

import com.ms.bf.card.config.ErrorCode;
import org.springframework.http.converter.HttpMessageNotReadableException;

public class CustomHttpMessageNotReadableException extends HttpMessageNotReadableException {


    public CustomHttpMessageNotReadableException(ErrorCode errorCode){
        super(String.valueOf(errorCode.getValue()));}

}
