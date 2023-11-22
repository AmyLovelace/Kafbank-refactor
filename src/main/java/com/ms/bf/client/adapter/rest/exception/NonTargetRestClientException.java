package com.ms.bf.client.adapter.rest.exception;

import com.ms.bf.client.config.ErrorCode;
import com.ms.bf.client.config.exception.GenericException;

public class NonTargetRestClientException extends GenericException {
    public NonTargetRestClientException(ErrorCode errorCode) {
        super(errorCode);
    }
}
