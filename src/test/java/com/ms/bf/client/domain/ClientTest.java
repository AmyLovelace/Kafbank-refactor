package com.ms.bf.client.domain;

import com.ms.bf.client.adapter.controller.model.clientValidator.ClientValidator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientTest {

    @Test
    public void shouldCreateAccountWithValidData() {
        Client client = Client.builder()
                .name("amy")
                .accountNumber("18.333.888-0")
                .age(38)
                .build();

        assertThat(client.getAccountNumber()).isEqualTo("18.333.888-0");
        assertThat(client.getAge()).isEqualTo(38);

        assertThat(client.getAccountNumber().length()).isEqualTo(12);
        assertThat(ClientValidator.isValidAccountNumber(client.getAccountNumber())).isTrue();
    }



}
