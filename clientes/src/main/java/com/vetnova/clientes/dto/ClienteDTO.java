package com.vetnova.clientes.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ClienteDTO {
    private Long idCliente;
    private String rut;
    private String nombre;
    private String telefono;
    private String email;
    private String direccion;
}