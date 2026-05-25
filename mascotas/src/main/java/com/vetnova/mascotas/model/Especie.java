package com.vetnova.mascotas.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "especies")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Especie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEspecie;

    @Column(nullable = false, unique = true, length = 50)
    private String nombre; // ej: Canino, Felino

    @Column(length = 255)
    private String descripcion;
}