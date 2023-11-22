package com.ms.bf.card.adapter.rest.exception;

import com.ms.bf.card.config.ErrorCode;
import com.ms.bf.card.config.exception.GenericException;

public class BadRequestRestClientException extends GenericException {
    public BadRequestRestClientException(ErrorCode errorCode) {
        super(errorCode);
    }
}
