package com.ms.bf.client.adapter.rest.client.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ms.bf.client.adapter.controller.model.clientValidator.ClientValidator;
import com.ms.bf.client.config.ErrorCode;
import com.ms.bf.client.config.exception.CardException;
import com.ms.bf.client.domain.Client;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.regex.Pattern;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClientModel {

    private static final String ACCOUNT_NUMBER_REGEX = "^[0-9]{1,2}(\\.[0-9]{3})*-[0-9Kk]$";

    private static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern.compile(ACCOUNT_NUMBER_REGEX);

    @JsonProperty("name")
    @NotNull
    String name;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotNull
    @javax.validation.constraints.Pattern(regexp = ACCOUNT_NUMBER_REGEX, message = "El número de cuenta no tiene el formato de RUT válido.")
    @NotEmpty(message = "el numero de cuenta debe tener un largo especifico")
    @JsonProperty("accountNumber")
    private String accountNumber;

    @JsonProperty("age")
    @NotNull
    private int age;


    public Client toRest(Client client){
        if (!ClientValidator.isValidAccountNumber(this.accountNumber)) {
            throw new CardException(ErrorCode.CLIENT_INVALID_REQUEST);
        }

        if (!ClientValidator.validateAge(this.age)) {
            throw new CardException(ErrorCode.CLIENT_INVALID_REQUEST);
        }

        if (!ClientValidator.validateName(this.name)) {
            throw new CardException(ErrorCode.INVALID_CLIENT_NAME);
        }

        return Client.builder()
                .name(accountNumber)
                .accountNumber(accountNumber)
                .age(age)
                .build();

    }

    public Client toClientDomain() {

        if (!ClientValidator.isValidAccountNumber(this.accountNumber)) {
            throw new CardException(ErrorCode.CLIENT_INVALID_REQUEST);
        }

        if (!ClientValidator.validateAge(this.age)) {
            throw new CardException(ErrorCode.CLIENT_INVALID_REQUEST);
        }

        if (!ClientValidator.validateName(this.name)) {
            throw new CardException(ErrorCode.INVALID_CLIENT_NAME);
        }

        return Client.builder()
                .name(this.name)
                .accountNumber(this.accountNumber)
                .age(this.age)
                .build();

    }



}
