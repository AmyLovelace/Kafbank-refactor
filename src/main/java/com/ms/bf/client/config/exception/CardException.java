package com.ms.bf.client.config.exception;

import com.ms.bf.client.config.ErrorCode;

public class CardException extends GenericException {


    public CardException(ErrorCode errorCode) {
        super(errorCode);
    }
}
