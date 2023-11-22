package com.ms.bf.card.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "card")
public class CardProperty {
    private String urlBase;
    private String urlAccount;
    private String urlType;


    public String getUrl(String url, String complement) {
        return urlBase.concat(url).concat(complement);
    }

}

