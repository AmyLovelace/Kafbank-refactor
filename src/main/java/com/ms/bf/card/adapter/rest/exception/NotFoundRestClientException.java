package com.ms.bf.card.adapter.rest.exception;

import com.ms.bf.card.config.ErrorCode;
import com.ms.bf.card.config.exception.GenericException;

public class NotFoundRestClientException extends GenericException {
    public NotFoundRestClientException(ErrorCode errorCode) {
        super(errorCode);
    }
}
