package com.ms.bf.client.application;

import com.ms.bf.client.adapter.kafka.client.KafkaProducerPort;
import com.ms.bf.client.application.usecase.CreateClientInUseCase;
import com.ms.bf.client.domain.Client;
import com.ms.bf.client.config.exception.GenericException;
import com.ms.bf.client.config.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateCardInUseCaseTest {

    @Mock
    private KafkaProducerPort kafkaProducerPort;

    @InjectMocks
    private CreateClientInUseCase createCardInUseCase;

    @Test
    public void shouldCreateCardSuccessfully() {
        Client client = Client.builder()
                .accountNumber("12.345.678-9")
                .age(30)
                .build();

        when(kafkaProducerPort.sendMessage(client)).thenReturn(1);

        Integer cardId = createCardInUseCase.create(client);

        assertThat(cardId).isEqualTo(1);
    }

    @Test
    public void shouldThrowCustomHttpMessageNotReadableExceptionWhenKafkaProducerFails() {
        Client client = Client.builder()
                .accountNumber("123456789")
                .age(30)
                .build();

        when(kafkaProducerPort.sendMessage(client)).thenThrow(new GenericException(ErrorCode.CLIENT_INVALID_REQUEST));

        assertThrows(GenericException.class, () -> {
            createCardInUseCase.create(client);
        });
    }

    @Test
    public void shouldCreateAccountWithValidData() throws Exception {
        Client client = Client.builder()
                .accountNumber("18.333.888-0")
                .age(38)
                .build();

        Integer cardId = createCardInUseCase.create(client);

        assertThat(cardId).isNotNull();
    }




}
