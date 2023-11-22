package com.ms.bf.client.application.usecase;

import com.ms.bf.client.adapter.kafka.client.KafkaProducerPort;
import com.ms.bf.client.application.port.in.BlockCardIn;
import com.ms.bf.client.domain.BlockCard;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class BlockCardInUseCase implements BlockCardIn {

    private final KafkaProducerPort kafkaProducerPort;

    @Override
    public Integer block(BlockCard blockedCard) {
        return null;
    }
}
