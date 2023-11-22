package com.ms.bf.card.adapter.rest.exception;

import com.ms.bf.card.config.ErrorCode;
import com.ms.bf.card.config.exception.GenericException;

public class RestClientGenericException extends GenericException {
    public RestClientGenericException(ErrorCode errorCode) {
        super(errorCode);
    }
}
