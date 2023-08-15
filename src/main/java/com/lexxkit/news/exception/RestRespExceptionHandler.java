package com.lexxkit.news.exception;

import java.util.NoSuchElementException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestRespExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {NoSuchElementException.class})
  protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
    String respBody = "No such element in DB";
    return handleExceptionInternal(ex, respBody,
        new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

}
