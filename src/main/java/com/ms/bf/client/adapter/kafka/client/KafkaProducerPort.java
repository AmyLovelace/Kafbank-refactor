package com.ms.bf.client.adapter.kafka.client;

import com.ms.bf.client.domain.Client;

public interface KafkaProducerPort {

    Integer sendMessage(Client client);
}
