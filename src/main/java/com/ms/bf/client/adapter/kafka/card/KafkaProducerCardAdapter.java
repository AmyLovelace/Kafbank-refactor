package com.ms.bf.client.adapter.kafka.card;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.bf.client.config.ErrorCode;
import com.ms.bf.client.config.exception.GenericException;
import com.ms.bf.client.config.property.KafkaProperty;
import com.ms.bf.client.domain.Card;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class KafkaProducerCardAdapter implements KafkaProducerCardPort{

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaProperty kafkaProperty;
    private final ObjectMapper objectMapper;

    public KafkaProducerCardAdapter(KafkaTemplate<String, String> kafkaTemplate, KafkaProperty kafkaProperty, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaProperty = kafkaProperty;
        this.objectMapper = objectMapper;
    }


    @Override
    public Integer sendCardRequest(Card card)throws GenericException {
        try {
            log.info("Message sent -> {}", card.toString());


            String json = objectMapper.writeValueAsString(card);


            Message<String> message = MessageBuilder.withPayload(json)
                    .setHeader(KafkaHeaders.TOPIC, kafkaProperty.getTopicCard())
                    .build();

            kafkaTemplate.send(message);
            log.info("Sent message value: {}", card);
            return 0;
        } catch (MessagingException e) {
            log.error("Error al generar el mensaje: ", e);
            throw new GenericException(ErrorCode.CLIENT_INVALID_REQUEST, "Error al generar el mensaje");
        } catch (JsonProcessingException e) {
            log.error("Error al serializar a JSON: ", e);
            throw new GenericException(ErrorCode.CLIENT_INVALID_REQUEST, "Error al serializar a JSON");
        }
    }
}