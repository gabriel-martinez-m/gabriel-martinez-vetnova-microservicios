package com.vetnova.clientes.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ContactoEmergenciaDTO {
    @NotBlank(message = "El nombre del contacto es obligatorio")
    private String nombre;
    @NotBlank(message = "El teléfono del contacto es obligatorio")
    private String telefono;
    @NotBlank(message = "El parentesco es obligatorio")
    private String parentesco;
}