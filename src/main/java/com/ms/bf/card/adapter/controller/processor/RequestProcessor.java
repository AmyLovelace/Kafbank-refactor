package com.ms.bf.card.adapter.controller.processor;

import com.ms.bf.card.adapter.controller.model.RestResponse;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RequestProcessor implements Processor {
    private static final String COMMA = ",";
    private static final String CONDITION = "[a-zA-Z0-9_]+=([\\p{InBasic_Latin}\\p{Inlatin_1_Supplement}]+/)";
    private static final Pattern pattern = Pattern.compile(CONDITION);

    @Override
    public <X> RestResponse<X> processRequest(final Enriched enriched, final Function<Enriched, RestResponse<X>> operation) {
        return operation.apply(enriched);
    }

    @Override
    public Map<String, String> parseFilters(final String filters) {
        return parseFilter(
                Arrays.asList(Optional.ofNullable(filters).orElse(StringUtils.EMPTY).split(COMMA)));
    }

    @Override
    public Map<String, String> buildMetadata(HttpServletRequest request) {
        return buildMetadata(request, null, null);
    }

    protected Map<String, String> parseFilter(final List<String> filters) {
        return Optional.ofNullable(filters).orElse(Collections.emptyList())
                .stream()
                .filter(f -> pattern.matcher(f).matches())
                .map(l -> l.split("=", 2))
                .collect(Collectors.toMap(k -> k[0], o -> o[0]));
    }

    @Override
    public Map<String, String> buildMetadata(final HttpServletRequest request, Integer pageNumber, Integer pageSize) {
        final var metadata = new HashMap<String, String>();
        Optional.ofNullable(pageNumber)
                .ifPresent(d -> metadata.put("page_number", String.valueOf(d)));
        Optional.ofNullable(pageSize)
                .ifPresent(d -> metadata.put("page_size", String.valueOf(d)));
        Optional.ofNullable(request.getQueryString())
                .ifPresent(d -> metadata.put("query_string", d));
        return metadata;
    }
}
