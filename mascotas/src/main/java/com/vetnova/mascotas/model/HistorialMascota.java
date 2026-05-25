package com.vetnova.mascotas.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "historiales_mascota")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class HistorialMascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistorial;

    @Column(nullable = false, unique = true)
    private String numeroHistoriaClinica; // Código correlativo único

    @Column(columnDefinition = "TEXT")
    private String resumenClinico;

    private LocalDate fechaUltimaAtencion;

    private Double ultimoPeso;

    private Boolean estaEsterilizado;

    @Column(length = 255)
    private String alergiasCriticas;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mascota", nullable = false, unique = true)
    private Mascota mascota;
}