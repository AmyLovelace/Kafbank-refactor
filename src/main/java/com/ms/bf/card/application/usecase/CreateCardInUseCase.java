package com.ms.bf.card.application.usecase;

import com.ms.bf.card.adapter.controller.model.card.AccountRest;
import com.ms.bf.card.adapter.kafka.KafkaProducerPort;
import com.ms.bf.card.adapter.rest.card.model.card.AccountModel;
import com.ms.bf.card.application.port.in.CreateIn;
import com.ms.bf.card.config.exception.CustomHttpMessageNotReadableException;
import com.ms.bf.card.domain.Account;
import com.ms.bf.card.config.exception.GenericException;
import com.ms.bf.card.config.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class CreateCardInUseCase implements CreateIn {

    private final KafkaProducerPort kafkaProducerPort;

    @Override
    public Integer create(Account account) throws GenericException {
        try {
            return kafkaProducerPort.sendMessage(account);
        } catch (RuntimeException e) {
            log.error("Error al generar el mensaje: ", e);
            throw new GenericException(ErrorCode.CARD_INVALID_REQUEST);
        }
    }
}
