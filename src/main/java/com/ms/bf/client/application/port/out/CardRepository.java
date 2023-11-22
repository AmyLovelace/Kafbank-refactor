package com.ms.bf.client.application.port.out;

import com.ms.bf.client.domain.Card;

public interface CardRepository {

    Card create(Card card);
}
