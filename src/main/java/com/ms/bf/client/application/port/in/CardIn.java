package com.ms.bf.client.application.port.in;

import com.ms.bf.client.domain.Card;
import com.ms.bf.client.domain.Client;

public interface CardIn {

    Integer create(Card card);
}
