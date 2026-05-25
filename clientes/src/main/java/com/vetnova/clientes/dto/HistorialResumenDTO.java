package com.vetnova.clientes.dto;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class HistorialResumenDTO {
    private Long idHistorial;
    private Integer totalCompras;
    private Integer totalAtenciones;
    private LocalDate fechaUltimaAtencion;
}