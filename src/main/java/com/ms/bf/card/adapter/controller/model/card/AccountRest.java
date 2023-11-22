package com.ms.bf.card.adapter.controller.model.card;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ms.bf.card.adapter.controller.model.CardValidator.CardValidator;
import com.ms.bf.card.adapter.rest.card.model.card.AccountModel;
import com.ms.bf.card.config.ErrorCode;
import com.ms.bf.card.config.exception.CardException;
import com.ms.bf.card.domain.Account;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccountRest {
    private static final String ACCOUNT_NUMBER_REGEX = "^[0-9]{1,2}(\\.[0-9]{3})*-[0-9Kk]$";


    private static final java.util.regex.Pattern ACCOUNT_NUMBER_PATTERN = java.util.regex.Pattern.compile(ACCOUNT_NUMBER_REGEX);

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotNull
    @Pattern(regexp = ACCOUNT_NUMBER_REGEX, message = "El número de cuenta no tiene el formato de RUT válido.")
    @NotEmpty(message = "el numero de cuenta debe tener un largo especifico")
    @JsonProperty("accountNumber")
    String accountNumber;

    @JsonProperty("age")
    @NotNull
    int age;

    @JsonCreator
    public AccountRest(@JsonProperty("accountNumber") String accountNumber, @JsonProperty("age")int age ) {
        this.accountNumber = accountNumber;
        this.age= age;
    }

    public static AccountRest toCardRest(Account card) {
        if (card == null) {
            return null;
        }

        return AccountRest.builder()
                .accountNumber(card.getAccountNumber())
                .age(card.getAge())
                .build();
    }


    public Account toCardDomain() {
        if (!CardValidator.isValidAccountNumber(this.accountNumber)) {
            throw new CardException(ErrorCode.INVALID_ACCOUNT_NUMBER);
        }

        if (!CardValidator.validateAge(this.age)) {
            throw new CardException(ErrorCode.INVALID_ACCOUNT_AGE);
        }

        return Account.builder()
                .accountNumber(this.accountNumber)
                .age(this.age)
                .build();
    }

    }

