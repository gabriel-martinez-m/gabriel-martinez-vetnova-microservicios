package com.vetnova.clientes.exception;

import lombok.*;
import java.time.LocalDateTime;
import java.util.Map;

@Getter @Setter
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private Map<String, String> errors; // Almacena campos validados fallidos
}