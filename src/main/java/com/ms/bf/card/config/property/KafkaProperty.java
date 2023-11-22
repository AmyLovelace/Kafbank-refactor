package com.ms.bf.card.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperty {

    private String topicName;
    private String retentionMsConfig;
    private String dummy;

}
