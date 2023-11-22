package com.ms.bf.client.adapter.rest.handler;

import com.ms.bf.client.adapter.rest.exception.RestClientGenericException;
import com.ms.bf.client.config.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.util.Map;

@AllArgsConstructor
public class RestTemplateErrorHandler implements ResponseErrorHandler {
    private final Map<HttpStatus, RuntimeException> exceptionMap;

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        throw exceptionMap.getOrDefault(response.getStatusCode(), new RestClientGenericException(ErrorCode.WEB_CLIENT_GENERIC));
    }
}
