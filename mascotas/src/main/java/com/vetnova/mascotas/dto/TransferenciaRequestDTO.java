package com.vetnova.mascotas.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class TransferenciaRequestDTO {
    @NotNull(message = "El ID del nuevo propietario es obligatorio")
    private Long idNuevoCliente;
}
