package com.ms.bf.client.adapter.rest.exception;

import com.ms.bf.client.config.ErrorCode;
import com.ms.bf.client.config.exception.GenericException;

public class NotFoundRestClientException extends GenericException {
    public NotFoundRestClientException(ErrorCode errorCode) {
        super(errorCode);
    }
}
