package com.ms.bf.card.adapter.rest.card.model.card;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ms.bf.card.adapter.controller.model.CardValidator.CardValidator;
import com.ms.bf.card.config.ErrorCode;
import com.ms.bf.card.config.exception.CardException;
import com.ms.bf.card.domain.Account;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.regex.Pattern;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccountModel {

    private static final String ACCOUNT_NUMBER_REGEX = "^[0-9]{1,2}(\\.[0-9]{3})*-[0-9Kk]$";

    private static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern.compile(ACCOUNT_NUMBER_REGEX);

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotNull
    @javax.validation.constraints.Pattern(regexp = ACCOUNT_NUMBER_REGEX, message = "El número de cuenta no tiene el formato de RUT válido.")
    @NotEmpty(message = "el numero de cuenta debe tener un largo especifico")
    @JsonProperty("cuenta")
    private String accountNumber;

    @JsonProperty("edad")
    @NotNull
    private int age;


    public Account toRest(Account card){
        if (!CardValidator.isValidAccountNumber(this.accountNumber)) {
            throw new CardException(ErrorCode.CARD_INVALID_REQUEST);
        }

        if (!CardValidator.validateAge(this.age)) {
            throw new CardException(ErrorCode.CARD_INVALID_REQUEST);
        }

        return Account.builder()
                .accountNumber(accountNumber)
                .age(age)
                .build();

    }

    public Account toCardDomain() {

        if (!CardValidator.isValidAccountNumber(this.accountNumber)) {
            throw new CardException(ErrorCode.CARD_INVALID_REQUEST);
        }

        if (!CardValidator.validateAge(this.age)) {
            throw new CardException(ErrorCode.CARD_INVALID_REQUEST);
        }

        return Account.builder()
                .accountNumber(this.accountNumber)
                .age(this.age)
                .build();

    }



}
