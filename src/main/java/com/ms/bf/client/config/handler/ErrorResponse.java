package com.ms.bf.client.config.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@NonNull
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ErrorResponse {
    private static final String PATTERN_DATE = "yyyy-MM-dd'T'HH:mm:ss[.SSSSSSSSS]['Z']";

    @JsonProperty
    private String name;

    @JsonProperty
    private String detail;

    @JsonProperty
    private String resource;

    @JsonProperty
    private Integer status;

    @JsonProperty
    private Integer code;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PATTERN_DATE)
    private LocalDateTime timestamp;

    @JsonProperty
    private Map<String, String> metadata;


}
