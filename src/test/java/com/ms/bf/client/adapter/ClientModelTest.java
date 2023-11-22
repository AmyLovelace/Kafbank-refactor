package com.ms.bf.client.adapter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ms.bf.client.adapter.controller.model.clientValidator.ClientValidator;
import com.ms.bf.client.adapter.rest.client.model.ClientModel;
import com.ms.bf.client.domain.Client;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.regex.Pattern;

import static org.mockito.Mockito.mock;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClientModelTest {

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

    @Mock
    private ClientValidator clientValidator;

    @Test
    public void testToRestValidInput() {
        ClientModel clientModel = mock(ClientModel.class);

        Client mockClient = AccountMock.createMockAccount("12.222.223-4", 25);
        Mockito.when(clientModel.toRest(clientModel.toClientDomain())).thenReturn(mockClient);

        Client client = clientModel.toRest(clientModel.toClientDomain());

        Assertions.assertEquals("12.222.223-4", client.getAccountNumber());
        Assertions.assertEquals(25, client.getAge());
    }
}
