package com.vetnova.usuarios.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
public class JwtResponseDTO {
    private String token;
    private String type = "Bearer";
    private String email;
    private Long expiresIn; // en segundos
}