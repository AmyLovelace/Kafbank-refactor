package com.ms.bf.client.application.usecase;

import com.ms.bf.client.adapter.kafka.card.KafkaProducerCardPort;
import com.ms.bf.client.adapter.kafka.client.KafkaProducerPort;
import com.ms.bf.client.application.port.in.CardIn;
import com.ms.bf.client.config.ErrorCode;
import com.ms.bf.client.config.exception.GenericException;
import com.ms.bf.client.domain.Card;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
@Slf4j
public class CreateCardInUseCase implements CardIn {

    private final KafkaProducerCardPort kafkaProducerPort;

    @Override
    public Integer create(Card card)throws GenericException {
        try {
            return kafkaProducerPort.sendCardRequest(card);
        } catch (RuntimeException e) {
            log.error("Error al generar el mensaje: ", e);
            throw new GenericException(ErrorCode.CARD_INVALID_REQUEST);
        }
    }
}
