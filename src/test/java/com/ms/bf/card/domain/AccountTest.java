package com.ms.bf.card.domain;

import com.ms.bf.card.adapter.controller.model.CardValidator.CardValidator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountTest {

    @Test
    public void shouldCreateAccountWithValidData() {
        Account account = Account.builder()
                .accountNumber("18.333.888-0")
                .age(38)
                .build();

        assertThat(account.getAccountNumber()).isEqualTo("18.333.888-0");
        assertThat(account.getAge()).isEqualTo(38);

        assertThat(account.getAccountNumber().length()).isEqualTo(12);
        assertThat(CardValidator.isValidAccountNumber(account.getAccountNumber())).isTrue();
    }



}
