package com.ms.bf.card.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BlockCard {
    String accountNumber;
    String cardNumber;
    int cardStatus;
    String descriptionStatus;
}
