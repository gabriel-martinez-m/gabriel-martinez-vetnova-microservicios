package com.vetnova.usuarios.repository;

import com.vetnova.usuarios.model.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermisoRepository extends JpaRepository<Permiso, Long> {
    // Hereda todos los métodos básicos
}
