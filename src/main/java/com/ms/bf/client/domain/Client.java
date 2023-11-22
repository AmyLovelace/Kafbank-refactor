package com.ms.bf.client.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Client {

    String name;

    String accountNumber;

    int age;


}
