package com.ms.bf.card.adapter.rest.exception;

import com.ms.bf.card.config.ErrorCode;
import com.ms.bf.card.config.exception.GenericException;

public class NonTargetRestClientException extends GenericException {
    public NonTargetRestClientException(ErrorCode errorCode) {
        super(errorCode);
    }
}
