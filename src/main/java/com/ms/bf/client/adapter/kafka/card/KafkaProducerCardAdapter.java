package com.ms.bf.client.adapter.kafka.card;

import com.ms.bf.client.domain.Card;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class KafkaProducerCardAdapter implements KafkaProducerCardPort{
    @Override
    public UUID sendCardRequest(Card card) {
        return null;
    }
}
