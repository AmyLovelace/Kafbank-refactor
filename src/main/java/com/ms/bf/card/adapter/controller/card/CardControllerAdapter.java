package com.ms.bf.card.adapter.controller.card;
import com.ms.bf.card.adapter.controller.model.card.AccountRest;
import com.ms.bf.card.application.port.in.CreateIn;
import lombok.extern.slf4j.Slf4j;

import com.ms.bf.card.adapter.controller.model.RestResponse;
import com.ms.bf.card.adapter.controller.processor.Processor;
import com.ms.bf.card.adapter.controller.processor.RequestProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/api/v1/card/")
public class CardControllerAdapter {

    private static final String CREATE_CARD = "/create";
    private final CreateIn createCardIn;
    private final Processor processor;


    public CardControllerAdapter( CreateIn createCardIn1) {
        this.createCardIn = createCardIn1;
        this.processor = new RequestProcessor();
    }

    @CrossOrigin
    @PostMapping(CREATE_CARD)
    public RestResponse<Integer> createCard(final HttpServletRequest httpServletRequest, @Valid @RequestBody AccountRest request )throws ExecutionException, InterruptedException{
        log.info("Llamada al servicio de creacion de tarjetas");
        var response =  createCardIn.create(request.toCardDomain());
        log.info("respuesta del servicio de creacion de tarjetas : [{}] " , response);
        return processor.processRequest(Processor.Enriched.of(httpServletRequest),
                res -> RestResponse.<Integer>builder()
                        .data(response)
                        .id(res.getId())
                       .status(HttpStatus.OK.value())
                       .resource(httpServletRequest.getRequestURI())
                       .metadata(processor.buildMetadata(res.getReq()))
                       .build()
        );

    }



}
