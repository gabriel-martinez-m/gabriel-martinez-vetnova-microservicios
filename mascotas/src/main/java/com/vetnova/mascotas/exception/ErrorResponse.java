package com.vetnova.mascotas.exception;

import lombok.*;
import java.time.LocalDateTime;
import java.util.Map;

@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private Map<String, String> errors;
}