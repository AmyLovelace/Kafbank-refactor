package com.ms.bf.client.adapter.controller.model.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ms.bf.client.adapter.controller.model.clientValidator.ClientValidator;
import com.ms.bf.client.config.ErrorCode;
import com.ms.bf.client.config.exception.CardException;
import com.ms.bf.client.domain.Client;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClientRest {
    private static final String ACCOUNT_NUMBER_REGEX = "^[0-9]{1,2}(\\.[0-9]{3})*-[0-9Kk]$";


    private static final java.util.regex.Pattern ACCOUNT_NUMBER_PATTERN = java.util.regex.Pattern.compile(ACCOUNT_NUMBER_REGEX);


    @JsonProperty("name")
    @NotNull
    String name;

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
    public ClientRest(@JsonProperty("accountNumber") String accountNumber, @JsonProperty("age")int age ) {
        this.accountNumber = accountNumber;
        this.age= age;
    }

    public static ClientRest toClientRest(Client client) {
        if (client == null) {
            return null;
        }

        return ClientRest.builder()
                .accountNumber(client.getAccountNumber())
                .age(client.getAge())
                .build();
    }


    public Client toCardDomain() {
        if (!ClientValidator.isValidAccountNumber(this.accountNumber)) {
            throw new CardException(ErrorCode.INVALID_ACCOUNT_NUMBER);
        }

        if (!ClientValidator.validateAge(this.age)) {
            throw new CardException(ErrorCode.INVALID_ACCOUNT_AGE);
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

