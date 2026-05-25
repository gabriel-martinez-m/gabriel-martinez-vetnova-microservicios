package com.vetnova.usuarios.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "permisos")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPermiso;

    @Column(nullable = false, unique = true, length = 50)
    private String nombrePermiso;

    @Column(nullable = false, length = 50)
    private String modulo;
}