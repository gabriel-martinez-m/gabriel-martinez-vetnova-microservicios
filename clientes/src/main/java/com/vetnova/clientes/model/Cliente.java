package com.vetnova.clientes.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @Column(nullable = false, unique = true, length = 20)
    private String rut;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 20)
    private String telefono;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 255)
    private String direccion;

    @Column(nullable = false, length = 30)
    private String estado; // ACTIVO, INACTIVO

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaUltimaModificacion;

    // Relación de Composición: 1 tiene 0..*
    @Builder.Default
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContactoEmergencia> contactosEmergencia = new ArrayList<>();

    // Relación de Composición: 1 posee 1
    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private HistorialCliente historialCliente;

    // Helpers para sincronización bidireccional
    public void agregarContacto(ContactoEmergencia contacto) {
        if (this.contactosEmergencia == null) {
            this.contactosEmergencia = new ArrayList<>();
        }
        this.contactosEmergencia.add(contacto);
        contacto.setCliente(this);
    }

    public void asignarHistorial(HistorialCliente historial) {
        this.historialCliente = historial;
        if (historial != null) {
            historial.setCliente(this);
        }
    }
}