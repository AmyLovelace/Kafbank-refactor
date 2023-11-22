package com.ms.bf.card.adapter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ms.bf.card.adapter.controller.model.CardValidator.CardValidator;
import com.ms.bf.card.adapter.rest.card.model.card.AccountModel;
import com.ms.bf.card.domain.Account;
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
public class AccountModelTest {

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
    private CardValidator cardValidator;

    @Test
    public void testToRestValidInput() {
        AccountModel accountModel = mock(AccountModel.class);

        Account mockAccount = AccountMock.createMockAccount("12.222.223-4", 25);
        Mockito.when(accountModel.toRest(accountModel.toCardDomain())).thenReturn(mockAccount);

        Account account = accountModel.toRest(accountModel.toCardDomain());

        Assertions.assertEquals("12.222.223-4", account.getAccountNumber());
        Assertions.assertEquals(25, account.getAge());
    }
}
