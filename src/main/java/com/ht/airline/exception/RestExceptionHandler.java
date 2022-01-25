package com.ht.airline.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ht.airline.dto.response.ErrResponse;
import com.ht.airline.dto.response.GenResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  ObjectMapper objectMapper;

  public RestExceptionHandler(Jackson2ObjectMapperBuilder mapperBuilder) {
    super();
    this.objectMapper = mapperBuilder.build();
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    String error = ex.getParameterName() + " parameter is missing";
    return new ResponseEntity<>(error, BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
    String requestDetails = getRequestDetails(request);
    log.error("Unhandled exception at {}. Reason: {}", requestDetails, ex.getMessage(), ex);
    GenResponse response = GenResponse.builder().build();
    response.setError(
        ErrResponse.builder().description(ex.getMessage()).errorCode(BAD_REQUEST.value()).build());
    response.setStatusCode(400);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  private String getRequestDetails(WebRequest request) {
    ServletWebRequest servletWebRequest = (ServletWebRequest) request;
    return servletWebRequest.getRequest().getRequestURI();
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    List<String> details = new ArrayList<>();
    for (ObjectError error : ex.getBindingResult().getAllErrors()) {
      details.add(error.getDefaultMessage());
    }
    GenResponse response = GenResponse.builder().build();
    response.setError(
        ErrResponse.builder().description(ex.getMessage()).errorCode(BAD_REQUEST.value()).build());
    response.setStatusCode(HttpStatus.BAD_REQUEST.value());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }
}
