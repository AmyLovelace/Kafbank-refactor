package com.ms.bf.card.config.exception;

import com.ms.bf.card.config.ErrorCode;

public class MethodArgumentNotValidException extends GenericException {
    protected MethodArgumentNotValidException(ErrorCode errorCode) {
        super(errorCode);
    }
}
