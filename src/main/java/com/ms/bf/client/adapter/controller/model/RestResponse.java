package com.ms.bf.client.adapter.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestResponse<X> {

    private String id;
    private Integer status;
    private String resource;
    private X data;
    private Map<String, String> metadata;
}
