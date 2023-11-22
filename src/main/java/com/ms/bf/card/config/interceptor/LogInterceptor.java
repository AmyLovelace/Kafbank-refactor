package com.ms.bf.card.config.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StopWatch;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
public class LogInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        traceRequest(request, body);
        StopWatch watch = new StopWatch();
        watch.start();
        ClientHttpResponse response = execution.execute(request, body);
        ClientHttpResponse wrapper = new HttpClientResponseInterceptor(response);
        watch.stop();
        traceResponse(wrapper, watch.getTotalTimeMillis());
        return  wrapper;
    }

    private void traceRequest(HttpRequest request, byte[] body){
        log.info("Request Url       : {}", request.getURI());
        log.info("Request Method    : {}", request.getMethod());
        log.info("Request Headers   : {}", request.getHeaders());
        log.info("Request Body      : {}", new String(body, StandardCharsets.UTF_8));
    }
    private void traceResponse(ClientHttpResponse response, long call) throws IOException {
        String bodyResponse = StreamUtils.copyToString(response.getBody(), Charset.defaultCharset());
        log.info("Response Status Code    : {}", response.getStatusCode());
        log.info("Response Status Text    : {}", response.getStatusText());
        log.info("Response Headers        : {}", response.getHeaders());
        log.info("Response Body           : {}", bodyResponse);
        log.info("Response Duration       : {}",call);
    }
}
