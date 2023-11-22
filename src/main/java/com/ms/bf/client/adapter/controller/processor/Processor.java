package com.ms.bf.client.adapter.controller.processor;

import com.ms.bf.client.adapter.controller.model.RestResponse;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.function.Function;

public interface Processor {

    Integer DEFAULT_PAGE_NUMBER = 0;
    Integer DEFAULT_PAGE_SIZE = 0;

    <X> RestResponse<X> processRequest(Enriched enriched, Function<Enriched, RestResponse<X>> operation);
    Map<String,String> parseFilters(String filters);
    Map<String,String> buildMetadata(HttpServletRequest request);
    Map<String,String> buildMetadata(HttpServletRequest request, Integer pageNumber, Integer pageSize);

    @Getter
    @RequiredArgsConstructor
    @EqualsAndHashCode
    final class Enriched {

        private final String id;
        private final HttpServletRequest req;
        private final Integer pageNumber;
        private final Integer pageSize;

        public static Enriched of(HttpServletRequest request) {
            return of("", request, DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
        }


        public static Enriched of(HttpServletRequest request, Integer pageNumber, Integer pageSize) {
            return new Enriched("", request, pageNumber, pageSize);
        }

        public static Enriched of(String id, HttpServletRequest request, Integer pageNumber, Integer pageSize) {
            return new Enriched(id, request, pageNumber, pageSize);
        }
    }
}
