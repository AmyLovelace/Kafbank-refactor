package com.ms.bf.card.adapter.kafka;

import com.ms.bf.card.adapter.controller.model.card.AccountRest;
import com.ms.bf.card.adapter.rest.card.model.card.AccountModel;
import com.ms.bf.card.domain.Account;

public interface KafkaProducerPort {

    Integer sendMessage(Account account);
}
