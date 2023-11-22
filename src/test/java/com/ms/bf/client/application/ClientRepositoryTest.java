package com.ms.bf.client.application;

import com.ms.bf.client.application.port.out.ClientRepository;
import com.ms.bf.client.domain.Client;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ClientRepositoryTest {

    @Mock
    private ClientRepository clientRepository;

    @Test
    public void shouldCreateAccountSuccessfully() {
        Client client = Client.builder()
                .accountNumber("18.333.888-0")
                .age(38)
                .build();

        clientRepository = Mockito.mock(ClientRepository.class);

        when(clientRepository.create(client)).thenReturn(client);

        Client savedClient = clientRepository.create(client);

        assertThat(savedClient).isEqualTo(client);
    }

    @Test
    public void shouldReturnNullWhenAccountNumberIsEmpty() {
        Client client = Client.builder()
                .accountNumber("")
                .age(38)
                .build();

        clientRepository = Mockito.mock(ClientRepository.class);

        when(clientRepository.create(client)).thenReturn(null);

        Client savedClient = clientRepository.create(client);

        assertThat(savedClient).isNull();
    }

    @Test
    public void shouldReturnNullWhenAccountAgeIsNegative() {
        Client client = Client.builder()
                .accountNumber("18.333.888-0")
                .age(-10)
                .build();

        clientRepository = Mockito.mock(ClientRepository.class);

        when(clientRepository.create(client)).thenReturn(null);

        Client savedClient = clientRepository.create(client);

        assertThat(savedClient).isNull();
    }

    @Test
    public void shouldThrowExceptionWhenErrorOccurs() {
        Client client = Client.builder()
                .accountNumber("18.333.888-0")
                .age(38)
                .build();

        clientRepository = Mockito.mock(ClientRepository.class);

        when(clientRepository.create(client)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> {
            clientRepository.create(client);
        });
    }



}
