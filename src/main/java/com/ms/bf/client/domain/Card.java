package com.ms.bf.client.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Card {

    String accountNumber;
    String membership;

}
