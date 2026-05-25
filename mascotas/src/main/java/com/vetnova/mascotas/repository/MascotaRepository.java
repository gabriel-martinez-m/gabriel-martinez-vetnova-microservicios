package com.vetnova.mascotas.repository;

import com.vetnova.mascotas.model.Mascota;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {

    // HU-MA05: Búsqueda cruzada por Nombre, Microchip o Número de Historia Clínica
    @Query("SELECT m FROM Mascota m JOIN m.historialMascota h WHERE " +
           "LOWER(m.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
           "LOWER(m.numeroMicrochip) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
           "LOWER(h.numeroHistoriaClinica) LIKE LOWER(CONCAT('%', :filtro, '%'))")
    Page<Mascota> buscarPacientesGlobal(@Param("filtro") String filtro, Pageable pageable);
}