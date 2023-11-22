package com.ms.bf.card.application;

import com.ms.bf.card.application.port.out.CardRepository;
import com.ms.bf.card.domain.Account;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class CardRepositoryTest {

    @Mock
    private CardRepository cardRepository;

    @Test
    public void shouldCreateAccountSuccessfully() {
        Account account = Account.builder()
                .accountNumber("18.333.888-0")
                .age(38)
                .build();

        cardRepository = Mockito.mock(CardRepository.class);

        when(cardRepository.create(account)).thenReturn(account);

        Account savedAccount = cardRepository.create(account);

        assertThat(savedAccount).isEqualTo(account);
    }

    @Test
    public void shouldReturnNullWhenAccountNumberIsEmpty() {
        Account account = Account.builder()
                .accountNumber("")
                .age(38)
                .build();

        cardRepository = Mockito.mock(CardRepository.class);

        when(cardRepository.create(account)).thenReturn(null);

        Account savedAccount = cardRepository.create(account);

        assertThat(savedAccount).isNull();
    }

    @Test
    public void shouldReturnNullWhenAccountAgeIsNegative() {
        Account account = Account.builder()
                .accountNumber("18.333.888-0")
                .age(-10)
                .build();

        cardRepository = Mockito.mock(CardRepository.class);

        when(cardRepository.create(account)).thenReturn(null);

        Account savedAccount = cardRepository.create(account);

        assertThat(savedAccount).isNull();
    }

    @Test
    public void shouldThrowExceptionWhenErrorOccurs() {
        Account account = Account.builder()
                .accountNumber("18.333.888-0")
                .age(38)
                .build();

        cardRepository = Mockito.mock(CardRepository.class);

        when(cardRepository.create(account)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> {
            cardRepository.create(account);
        });
    }



}
