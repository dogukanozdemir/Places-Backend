package com.places.demo.config.exception;

import com.places.demo.config.exception.dto.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@ControllerAdvice
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<Object> defaultErrorHandler(
      HttpServletRequest req, HttpServletResponse res, Exception e) {
    return new ResponseEntity<>(
        ExceptionResponse.builder().time(LocalDateTime.now()).error(e.getMessage()).build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
