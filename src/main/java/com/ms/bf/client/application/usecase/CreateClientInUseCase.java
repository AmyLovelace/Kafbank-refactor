package com.ms.bf.client.application.usecase;

import com.ms.bf.client.adapter.kafka.client.KafkaProducerPort;
import com.ms.bf.client.application.port.in.CreateIn;
import com.ms.bf.client.domain.Client;
import com.ms.bf.client.config.exception.GenericException;
import com.ms.bf.client.config.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class CreateClientInUseCase implements CreateIn {

    private final KafkaProducerPort kafkaProducerPort;

    @Override
    public Integer create(Client client) throws GenericException {
        try {
            return kafkaProducerPort.sendMessage(client);
        } catch (RuntimeException e) {
            log.error("Error al generar el mensaje: ", e);
            throw new GenericException(ErrorCode.CLIENT_INVALID_REQUEST);
        }
    }
}
