package com.ms.bf.card.config.exception;

import com.ms.bf.card.config.ErrorCode;

public class CardException extends GenericException {


    public CardException(ErrorCode errorCode) {
        super(errorCode);
    }
}
