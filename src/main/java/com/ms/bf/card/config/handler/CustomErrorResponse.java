package com.ms.bf.card.config.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDateTime;
@Builder
@NonNull
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CustomErrorResponse {
    private static final String PATTERN_DATE = "yyyy-MM-dd'T'HH:mm:ss[.SSSSSSSSS]['Z']";

    @JsonProperty
    private String message;

    @JsonProperty
    private String resource;

    @JsonProperty
    private Integer status;

    @JsonProperty
    private Integer internalCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PATTERN_DATE)
    private LocalDateTime timestamp;

}
