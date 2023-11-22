package com.ms.bf.client.adapter.rest.card.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ms.bf.client.adapter.controller.model.clientValidator.ClientValidator;
import com.ms.bf.client.config.ErrorCode;
import com.ms.bf.client.config.exception.CardException;
import com.ms.bf.client.domain.Card;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CardModel {

    @NotEmpty(message = "el numero de cuenta debe tener un largo especifico")
    @JsonProperty("accountNumber")
    private String accountNumber;

    @NotEmpty(message = "la membresia debe ser o Standard o Premium")
    @JsonProperty("membership")
    private String membership;

    public Card toCardDomain() {

        if (!ClientValidator.isValidAccountNumber(this.accountNumber)) {
            throw new CardException(ErrorCode.CLIENT_INVALID_REQUEST);
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
