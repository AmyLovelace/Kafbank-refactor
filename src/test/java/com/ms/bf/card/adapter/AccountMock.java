package com.ms.bf.card.adapter;

import com.ms.bf.card.domain.Account;
import org.mockito.Mockito;

public class AccountMock {

    public static Account createMockAccount(String accountNumber, int age) {
        Account mockAccount = Mockito.mock(Account.class);
        Mockito.when(mockAccount.getAccountNumber()).thenReturn(accountNumber);
        Mockito.when(mockAccount.getAge()).thenReturn(age);
        return mockAccount;
    }
}
