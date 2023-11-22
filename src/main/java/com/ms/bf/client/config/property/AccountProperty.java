package com.ms.bf.client.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "account")
public class AccountProperty {
    private String urlBase;
    private String urlGetAccount;

    public String getUrl(String url, String complement) {
        return urlBase.concat(url).concat(complement);
    }

}
