package com.ms.bf.client.adapter.controller.card;

import com.ms.bf.client.adapter.controller.model.RestResponse;
import com.ms.bf.client.adapter.controller.model.card.CardRest;
import com.ms.bf.client.adapter.controller.model.client.ClientRest;
import com.ms.bf.client.adapter.controller.processor.Processor;
import com.ms.bf.client.adapter.controller.processor.RequestProcessor;
import com.ms.bf.client.application.port.in.CardIn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/api/v1/card/")
public class CardControllerAdapter {
    private static final String CREATE_CARD= "/create";

    private final CardIn cardIn;
    private final Processor processor;

    public CardControllerAdapter(CardIn cardIn) {
        this.cardIn = cardIn;
        this.processor =  new RequestProcessor();
    }

    @CrossOrigin
    @PostMapping(CREATE_CARD)
    public RestResponse<UUID> createCard(final HttpServletRequest httpServletRequest, @Valid @RequestBody CardRest request )throws ExecutionException, InterruptedException{
        log.info("Llamada al servicio de creacion de tarjeta");
        var response =  cardIn.create(request.toCardDomain());
        log.info("respuesta del servicio de creacion tarjeta : [{}] " , response);
        return processor.processRequest(Processor.Enriched.of(httpServletRequest),
                res -> RestResponse.<UUID>builder()
                        .data(response)
                        .id(res.getId())
                        .status(HttpStatus.OK.value())
                        .resource(httpServletRequest.getRequestURI())
                        .metadata(processor.buildMetadata(res.getReq()))
                        .build()
        );

    }


}
