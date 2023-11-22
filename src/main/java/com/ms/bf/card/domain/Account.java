package com.ms.bf.card.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Account{

    String name;

    String accountNumber;

    int age;



}
