package com.ms.bf.card.application.port.out;

import com.ms.bf.card.domain.Account;


public interface CardRepository {

    Account create(Account account);


}
