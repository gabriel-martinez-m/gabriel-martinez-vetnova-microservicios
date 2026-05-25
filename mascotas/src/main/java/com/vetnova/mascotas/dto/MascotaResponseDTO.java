package com.vetnova.mascotas.dto;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class MascotaResponseDTO {
    private Long idMascota;
    private String nombre;
    private String especie;
    private String raza;
    private String sexo;
    private LocalDate fechaNacimiento;
    private String edadCalculada; // Calculada dinámicamente en tiempo de ejecución (HU-MA04)
    private Long idCliente;
    private String estado;
    private String numeroHistoriaClinica;
    private Double ultimoPeso;
    private Boolean estaEsterilizado;
    private String alergiasCriticas;
    private String resumenClinico;
}
