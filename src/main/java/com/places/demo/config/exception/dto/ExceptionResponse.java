package com.places.demo.config.exception.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExceptionResponse(LocalDateTime time, String error, Map<String, String> errors) {}
