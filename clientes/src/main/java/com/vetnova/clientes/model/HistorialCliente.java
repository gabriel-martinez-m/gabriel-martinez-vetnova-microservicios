package com.vetnova.clientes.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "historiales_cliente")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class HistorialCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistorial;

    @Column(nullable = false)
    private Integer totalCompras;

    @Column(nullable = false)
    private Integer totalAtenciones;

    private LocalDate fechaUltimaAtencion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false, unique = true)
    private Cliente cliente;
}