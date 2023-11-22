package com.ms.bf.client.config.interceptor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class HttpClientResponseInterceptor implements ClientHttpResponse {
    private final ClientHttpResponse original;
    private final byte[] body;

    public HttpClientResponseInterceptor(ClientHttpResponse original) throws IOException {
        this.original = original;
        InputStream data = original.getBody();
        this.body = StreamUtils.copyToByteArray(data);
        data.close();
    }

    @Override
    public HttpStatus getStatusCode() throws IOException {
        return original.getStatusCode();
    }

    @Override
    public int getRawStatusCode() throws IOException {
        return original.getRawStatusCode();
    }

    @Override
    public String getStatusText() throws IOException {
        return original.getStatusText();
    }

    @Override
    public void close() {
        original.close();
    }
    @Override
    public HttpHeaders getHeaders() {
        return original.getHeaders();
    }

    @Override
    public InputStream getBody() throws IOException {
        return new ByteArrayInputStream(body);
    }
}
