package com.ms.bf.card.application;

import com.ms.bf.card.adapter.kafka.KafkaProducerPort;
import com.ms.bf.card.application.usecase.CreateCardInUseCase;
import com.ms.bf.card.domain.Account;
import com.ms.bf.card.config.exception.CustomHttpMessageNotReadableException;
import com.ms.bf.card.config.exception.GenericException;
import com.ms.bf.card.config.ErrorCode;
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
    private CreateCardInUseCase createCardInUseCase;

    @Test
    public void shouldCreateCardSuccessfully() {
        Account account = Account.builder()
                .accountNumber("12.345.678-9")
                .age(30)
                .build();

        when(kafkaProducerPort.sendMessage(account)).thenReturn(1);

        Integer cardId = createCardInUseCase.create(account);

        assertThat(cardId).isEqualTo(1);
    }

    @Test
    public void shouldThrowCustomHttpMessageNotReadableExceptionWhenKafkaProducerFails() {
        Account account = Account.builder()
                .accountNumber("123456789")
                .age(30)
                .build();

        when(kafkaProducerPort.sendMessage(account)).thenThrow(new GenericException(ErrorCode.CARD_INVALID_REQUEST));

        assertThrows(GenericException.class, () -> {
            createCardInUseCase.create(account);
        });
    }

    @Test
    public void shouldCreateAccountWithValidData() throws Exception {
        Account account = Account.builder()
                .accountNumber("18.333.888-0")
                .age(38)
                .build();

        Integer cardId = createCardInUseCase.create(account);

        assertThat(cardId).isNotNull();
    }




}
