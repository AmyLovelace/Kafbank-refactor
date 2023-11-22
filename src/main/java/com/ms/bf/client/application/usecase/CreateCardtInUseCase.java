package com.ms.bf.client.application.usecase;

import com.ms.bf.client.adapter.kafka.client.KafkaProducerPort;
import com.ms.bf.client.application.port.in.CardIn;
import com.ms.bf.client.domain.Card;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
@Slf4j
public class CreateCardtInUseCase implements CardIn {

    private final KafkaProducerPort kafkaProducerPort;

    @Override
    public Integer create(Card card) {
        return null;
    }
}
