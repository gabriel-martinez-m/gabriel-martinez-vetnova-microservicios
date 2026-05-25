package com.vetnova.mascotas.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class MascotaRequestDTO {

    @NotBlank(message = "El nombre de la mascota es obligatorio")
    private String nombre;

    @NotBlank(message = "La especie es obligatoria")
    private String especieNombre; // ej: Canino, Felino

    @NotBlank(message = "La raza es obligatoria")
    private String raza;

    @NotBlank(message = "El sexo es obligatorio")
    @Pattern(regexp = "^(Macho|Hembra)$", message = "El sexo debe ser 'Macho' o 'Hembra'")
    private String sexo;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    @NotNull(message = "El ID del cliente/propietario es obligatorio")
    private Long idCliente;

    private String numeroMicrochip;
    
    private Double ultimoPeso;
    private Boolean estaEsterilizado;
    private String alergiasCriticas;
}