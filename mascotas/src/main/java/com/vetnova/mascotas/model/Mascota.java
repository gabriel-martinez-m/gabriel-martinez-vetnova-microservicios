package com.vetnova.mascotas.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "mascotas")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMascota;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String raza;

    @Column(nullable = false, length = 20)
    private String sexo; // Macho, Hembra

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Column(nullable = false)
    private Long idCliente; // Enlace lógico al Microservicio de Clientes

    @Column(nullable = false, length = 30)
    private String estado; // ACTIVO, FALLECIDO, EXTRAVIADO, DONADO

    @Column(unique = true, length = 50)
    private String numeroMicrochip;

    // Relación N pertenece a 1 (Catálogo Maestro Parametrizado)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_especie", nullable = false)
    private Especie especie;

    // Relación de Composición 1 posee 1
    @OneToOne(mappedBy = "mascota", cascade = CascadeType.ALL, orphanRemoval = true)
    private HistorialMascota historialMascota;

    // Helper para mantener la consistencia en cascada de la composición
    public void asignarHistorial(HistorialMascota historial) {
        this.historialMascota = historial;
        if (historial != null) {
            historial.setMascota(this);
        }
    }
}