package com.ms.bf.client.application.port.in;

import com.ms.bf.client.domain.Card;

import java.util.UUID;

public interface CardIn {

    UUID create(Card card);
}
