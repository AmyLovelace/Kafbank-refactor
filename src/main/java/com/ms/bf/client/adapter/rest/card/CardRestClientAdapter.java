package com.ms.bf.client.adapter.rest.card;

import com.ms.bf.client.adapter.rest.card.model.CardModel;
import com.ms.bf.client.adapter.rest.client.model.ClientModel;
import com.ms.bf.client.adapter.rest.exception.BadRequestRestClientException;
import com.ms.bf.client.adapter.rest.exception.EmptyOrNullBodyRestClientException;
import com.ms.bf.client.adapter.rest.exception.NotFoundRestClientException;
import com.ms.bf.client.adapter.rest.exception.TimeoutRestClientException;
import com.ms.bf.client.adapter.rest.handler.RestTemplateErrorHandler;
import com.ms.bf.client.application.port.out.CardRepository;
import com.ms.bf.client.config.ErrorCode;
import com.ms.bf.client.config.property.KafkaProperty;
import com.ms.bf.client.domain.Card;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;


@Slf4j
@Repository
public class CardRestClientAdapter implements CardRepository {


    private final RestTemplate restTemplate;

    private final KafkaProperty property;

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
    public Card create(Card card) {
        log.info("Servicio crear tarjeta, lo conecta a: [{}]" ,property.getTopicCard());
        CardModel response = Optional.ofNullable(restTemplate.postForObject(property.getTopicName(), card, CardModel.class))
                .orElseThrow(()-> new EmptyOrNullBodyRestClientException(ErrorCode.CLIENT_INVALID_REQUEST));
        log.info("respuesta del metodo create de Card [{}]", response);
        return response.toCardDomain();
    }
}
