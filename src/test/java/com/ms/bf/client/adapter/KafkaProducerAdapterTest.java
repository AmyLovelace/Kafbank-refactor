package com.ms.bf.client.adapter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ms.bf.client.adapter.kafka.client.KafkaProducerAdapter;
import com.ms.bf.client.config.property.KafkaProperty;
import com.ms.bf.client.domain.Client;
import com.ms.bf.client.config.exception.GenericException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.LoggerFactory;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaProducerAdapterTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private KafkaProperty kafkaProperty;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private KafkaProducerAdapter kafkaProducerAdapter;

    @BeforeEach
    void setUp() {
        lenient().when(kafkaProperty.getTopicName()).thenReturn("testTopic");
    }

    @Test
    void testSendMessage() throws GenericException, JsonProcessingException {
        Client client = mock(Client.class);
        when(client.toString()).thenReturn("Client{name='test', accountNumber='123', age=30}");
        when(objectMapper.writeValueAsString(client)).thenReturn("{\"name\":\"test\", \"accountNumber\":\"123\", \"age\":30}");

        kafkaProducerAdapter.sendMessage(client);

        verify(kafkaTemplate, times(1)).send(any(Message.class));
    }

    @Test
    void testSendMessage_JsonProcessingException() throws JsonProcessingException {
        Client client = mock(Client.class);
        when(client.toString()).thenReturn("Client{name='test', accountNumber='123', age=30}");
        doThrow(JsonProcessingException.class).when(objectMapper).writeValueAsString(client);

        assertThrows(GenericException.class, () -> {
            kafkaProducerAdapter.sendMessage(client);
        });
    }
    @Test
    void testSendMessage_MessagingException() throws MessagingException, JsonProcessingException {
        Client client = mock(Client.class);
        when(client.toString()).thenReturn("Client{name='test', accountNumber='123', age=30}");
        when(objectMapper.writeValueAsString(client)).thenReturn("{\"name\":\"test\", \"accountNumber\":\"123\", \"age\":30}");
        doThrow(MessagingException.class).when(kafkaTemplate).send(any(Message.class));

        assertThrows(GenericException.class, () -> {
            kafkaProducerAdapter.sendMessage(client);
        });
    }
    @Test
    void testSendMessage_LogsSentMessage() throws GenericException, JsonProcessingException {
        Client client = mock(Client.class);
        when(client.toString()).thenReturn("Client{name='test', accountNumber='123', age=30}");
        when(objectMapper.writeValueAsString(client)).thenReturn("{\"name\":\"test\", \"accountNumber\":\"123\", \"age\":30}");

        kafkaProducerAdapter.sendMessage(client);

        ArgumentCaptor<String> logCaptor = ArgumentCaptor.forClass(String.class);
        assertTrue(logCaptor.getValue().contains("Sent message value: Client{name='test', accountNumber='123', age=30}"));
    }




}
