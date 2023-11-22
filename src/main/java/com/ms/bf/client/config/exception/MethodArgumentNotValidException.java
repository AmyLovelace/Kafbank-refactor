package com.ms.bf.client.config.exception;

import com.ms.bf.client.config.ErrorCode;

public class MethodArgumentNotValidException extends GenericException {
    protected MethodArgumentNotValidException(ErrorCode errorCode) {
        super(errorCode);
    }
}
