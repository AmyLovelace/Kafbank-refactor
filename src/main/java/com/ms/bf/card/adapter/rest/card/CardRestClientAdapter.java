package com.ms.bf.card.adapter.rest.card;

import com.ms.bf.card.adapter.rest.card.model.card.AccountModel;
import com.ms.bf.card.adapter.rest.exception.BadRequestRestClientException;
import com.ms.bf.card.adapter.rest.exception.EmptyOrNullBodyRestClientException;
import com.ms.bf.card.adapter.rest.exception.NotFoundRestClientException;
import com.ms.bf.card.adapter.rest.exception.TimeoutRestClientException;
import com.ms.bf.card.adapter.rest.handler.RestTemplateErrorHandler;
import com.ms.bf.card.application.port.out.CardRepository;
import com.ms.bf.card.config.ErrorCode;
import com.ms.bf.card.config.property.KafkaProperty;
import com.ms.bf.card.domain.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class CardRestClientAdapter implements CardRepository{

    private final RestTemplate restTemplate;

    private KafkaProperty property;



    public CardRestClientAdapter(RestTemplate restTemplate, KafkaProperty property) {
        this.restTemplate = restTemplate;
        this.property = property;
        var errorHandler = new RestTemplateErrorHandler(
                Map.of(
                        HttpStatus.NOT_FOUND, new NotFoundRestClientException(ErrorCode.ACCOUNT_NOT_FOUND),
                        HttpStatus.REQUEST_TIMEOUT, new TimeoutRestClientException(ErrorCode.CARD_TIMEOUT),
                        HttpStatus.BAD_REQUEST, new BadRequestRestClientException(ErrorCode.CARD_BAD_REQUEST)
                )
        );
        this.restTemplate.setErrorHandler(errorHandler);
    }

    @Override
    public Account create(Account account) {
        log.info("Servicio crear tarjeta, lo conecta a: [{}]" ,property.getTopicName());
        AccountModel response = Optional.ofNullable(restTemplate.postForObject(property.getTopicName(),account, AccountModel.class))
                .orElseThrow(()-> new EmptyOrNullBodyRestClientException(ErrorCode.CARD_INVALID_REQUEST));
        log.info("popo[{}]", response);
        return response.toCardDomain();

    }


}
