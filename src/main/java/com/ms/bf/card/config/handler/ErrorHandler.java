package com.ms.bf.card.config.handler;

import com.ms.bf.card.adapter.rest.exception.BadRequestRestClientException;
import com.ms.bf.card.adapter.rest.exception.EmptyOrNullBodyRestClientException;
import com.ms.bf.card.adapter.rest.exception.NonTargetRestClientException;
import com.ms.bf.card.adapter.rest.exception.NotFoundRestClientException;
import com.ms.bf.card.adapter.rest.exception.RestClientGenericException;
import com.ms.bf.card.adapter.rest.exception.TimeoutRestClientException;
import com.ms.bf.card.config.ErrorCode;
import com.ms.bf.card.config.exception.CardException;
import com.ms.bf.card.config.exception.CustomHttpMessageNotReadableException;
import com.ms.bf.card.config.exception.GenericException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ErrorHandler {
    private static final String DEVELOP_PROFILE = "dev";
    private static final String LOCAL_PROFILE = "local";
    private final HttpServletRequest request;

    @Value("${spring.profiles.active:}")
    private String profile;

    public ErrorHandler(final HttpServletRequest request) {
        this.request = request;
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handle(Throwable e) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e);
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR,e, ErrorCode.INTERNAL_ERROR);
    }
    @ExceptionHandler(CustomHttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleCustomHttpMessageNotReadableException(CustomHttpMessageNotReadableException ex) {
        log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex);

        final var isDebugMessage = DEVELOP_PROFILE.equals(profile) || LOCAL_PROFILE.equals(profile) ? Arrays.toString(ex.getStackTrace()) : "";
        final var queryString = Optional.ofNullable(request.getQueryString()).orElse("");
        final var metaData = Map.of(
                "query_string", queryString,
                "stack_trace", isDebugMessage);

        return buildError(HttpStatus.BAD_REQUEST,ex,ErrorCode.CARD_INVALID_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex);

        final var isDebugMessage = DEVELOP_PROFILE.equals(profile) || LOCAL_PROFILE.equals(profile) ? Arrays.toString(ex.getStackTrace()) : "";
        final var queryString = Optional.ofNullable(request.getQueryString()).orElse("");
        final var metaData = Map.of(
                "query_string", queryString,
                "stack_trace", isDebugMessage);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .name(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .detail("Error al parsear el cuerpo de la solicitud. Por favor, verifique que el cuerpo de la solicitud sea un JSON válido.")
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ErrorCode.CARD_INVALID_REQUEST.getValue())
                .resource(request.getRequestURI())
                .metadata(metaData)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler({NonTargetRestClientException.class, RestClientGenericException.class})
    public ResponseEntity<ErrorResponse> handle(NonTargetRestClientException e) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e);
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR,e,e.getCode());
    }
    @ExceptionHandler({NotFoundRestClientException.class, EmptyOrNullBodyRestClientException.class})
    public ResponseEntity<ErrorResponse> handle(GenericException e) {
        log.error(HttpStatus.NOT_FOUND.getReasonPhrase(), e);
        return buildError(HttpStatus.NOT_FOUND,e,e.getCode());
    }
    @ExceptionHandler({BadRequestRestClientException.class})
    public ResponseEntity<ErrorResponse> handle(BadRequestRestClientException e) {
        log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), e);
        return buildError(HttpStatus.BAD_REQUEST,e,e.getCode());
    }

    @ExceptionHandler({CardException.class})
    public ResponseEntity<ErrorResponse> handle(CardException e) {
        log.error(HttpStatus.FORBIDDEN.getReasonPhrase(), e);
        return buildError(HttpStatus.BAD_REQUEST,e,e.getCode());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<String> errorMessages = fieldErrors.stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        String detail = String.join(", ", errorMessages);

        final var isDebugMessage = DEVELOP_PROFILE.equals(profile) || LOCAL_PROFILE.equals(profile) ? Arrays.toString(ex.getStackTrace()) : "";
        final var queryString = Optional.ofNullable(request.getQueryString()).orElse("");
        final var metaData = Map.of(
                "query_string", queryString,
                "stack_trace", isDebugMessage);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .name(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .detail(detail)
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ErrorCode.CARD_BAD_REQUEST.getValue())
                .resource(request.getRequestURI())
                .metadata(metaData)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }




    @ExceptionHandler({TimeoutRestClientException.class})
        public ResponseEntity<ErrorResponse> handle(TimeoutRestClientException e) {
            log.error(HttpStatus.REQUEST_TIMEOUT.getReasonPhrase(), e);
            return buildError(HttpStatus.REQUEST_TIMEOUT,e,e.getCode());
        }

    private ResponseEntity<ErrorResponse> buildError(HttpStatus httpStatus, Throwable e, ErrorCode code) {
        final var isDebugMessage = DEVELOP_PROFILE.equals(profile) || LOCAL_PROFILE.equals(profile) ? Arrays.toString(e.getStackTrace()) : "";
        final var queryString = Optional.ofNullable(request.getQueryString()).orElse("");
        final var metaData = Map.of(
                "query_string", queryString,
                "stack_trace", isDebugMessage);
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .name(httpStatus.getReasonPhrase())
                        .detail(String.format("%s: %s", e.getClass().getCanonicalName(), e.getMessage()))
                        .status(httpStatus.value())
                        .code(code.getValue())
                        .resource(request.getRequestURI())
                        .metadata(metaData)
                        .build(), httpStatus);

    }
    private ResponseEntity<CustomErrorResponse> customBuildError (HttpStatus httpStatus, Throwable e, ErrorCode code) {
        final var isDebugMessage = DEVELOP_PROFILE.equals(profile) || LOCAL_PROFILE.equals(profile) ? Arrays.toString(e.getStackTrace()) : "";
        final var queryString = Optional.ofNullable(request.getQueryString()).orElse("");
        final var metaData = Map.of(
                "query_string", queryString,
                "stack_trace", isDebugMessage);
        return new ResponseEntity<>(
                CustomErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .message(code.getReasons())
                        .status(httpStatus.value())
                        .internalCode(code.getValue())
                        .resource(request.getRequestURI())
                        .build(), httpStatus);

    }
}
