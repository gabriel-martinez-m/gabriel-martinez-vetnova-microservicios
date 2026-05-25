package com.vetnova.mascotas.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transferencias_propietario")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class TransferenciaPropietario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransferencia;

    @Column(nullable = false)
    private Long idMascota;

    @Column(nullable = false)
    private Long idClienteAnterior;

    @Column(nullable = false)
    private Long idClienteNuevo;

    @Column(nullable = false)
    private LocalDateTime fechaTransferencia;
}