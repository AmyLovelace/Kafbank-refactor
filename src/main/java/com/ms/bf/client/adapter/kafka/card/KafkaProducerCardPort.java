package com.ms.bf.client.adapter.kafka.card;

import com.ms.bf.client.domain.Card;

import java.util.UUID;

public interface KafkaProducerCardPort {

    Integer sendCardRequest(Card card);
}
