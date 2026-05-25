package com.vetnova.clientes.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponseDTO {
    private Long idCliente;
    private String rut;
    private String nombre;
    private String telefono;
    private String email;
    private String direccion;
    private String estado;
    private LocalDateTime fechaCreacion;
    private List<ContactoEmergenciaDTO> contactos;
    private HistorialResumenDTO historial;
}