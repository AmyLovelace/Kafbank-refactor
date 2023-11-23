package com.ms.bf.client.adapter.controller.client;
import com.ms.bf.client.adapter.controller.model.client.ClientRest;
import com.ms.bf.client.application.port.in.CreateIn;
import lombok.extern.slf4j.Slf4j;

import com.ms.bf.client.adapter.controller.model.RestResponse;
import com.ms.bf.client.adapter.controller.processor.Processor;
import com.ms.bf.client.adapter.controller.processor.RequestProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/api/v1/client/")
public class ClientControllerAdapter {

    private static final String CREATE_CLIENT= "/create";
    private final CreateIn createCardIn;
    private final Processor processor;


    public ClientControllerAdapter(CreateIn createCardIn1) {
        this.createCardIn = createCardIn1;
        this.processor = new RequestProcessor();
    }

    @CrossOrigin
    @PostMapping(CREATE_CLIENT)
    public RestResponse<Integer> createClient(final HttpServletRequest httpServletRequest, @Valid @RequestBody ClientRest request )throws ExecutionException, InterruptedException{
        log.info("Llamada al servicio de creacion de cliente");
        var response =  createCardIn.create(request.toClientDomain());
        log.info("respuesta del servicio de creacion de clientes : [{}] " , response);
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
