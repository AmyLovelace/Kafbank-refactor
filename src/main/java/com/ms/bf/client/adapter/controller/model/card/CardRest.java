package com.ms.bf.client.adapter.controller.model.card;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ms.bf.client.adapter.controller.model.clientValidator.ClientValidator;
import com.ms.bf.client.config.ErrorCode;
import com.ms.bf.client.config.exception.CardException;
import com.ms.bf.client.domain.Card;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CardRest {

    @NotEmpty(message = "el numero de cuenta debe tener un largo especifico")
    @JsonProperty("accountNumber")
    String accountNumber;

    @NotEmpty(message = "la membresia debe ser o Standard o Premium")
    @JsonProperty("membership")
    String membership;

    @JsonCreator
    public CardRest(@JsonProperty("accountNumber") String accountNumber, @JsonProperty("membership")String membership ) {
        this.accountNumber = accountNumber;
        this.membership = membership;
    }

    public Card toCardDomain() {
        if (!ClientValidator.isValidAccountNumber(this.accountNumber)) {
            throw new CardException(ErrorCode.INVALID_ACCOUNT_NUMBER);
        }

        if (!ClientValidator.validateMembership(this.membership)) {
            throw new CardException(ErrorCode.CLIENT_INVALID_REQUEST);
        }
        return Card.builder()
                .accountNumber(this.accountNumber)
                 .membership(this.membership)
                .build();
    }

}
