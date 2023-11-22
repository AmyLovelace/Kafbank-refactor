package com.ms.bf.client.adapter;

import com.ms.bf.client.domain.Client;
import org.mockito.Mockito;

public class AccountMock {

    public static Client createMockAccount(String accountNumber, int age) {
        Client mockClient = Mockito.mock(Client.class);
        Mockito.when(mockClient.getAccountNumber()).thenReturn(accountNumber);
        Mockito.when(mockClient.getAge()).thenReturn(age);
        return mockClient;
    }
}
