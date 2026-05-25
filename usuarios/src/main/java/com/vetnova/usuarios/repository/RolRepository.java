package com.vetnova.usuarios.repository;

import com.vetnova.usuarios.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Long> {
    // Hereda todos los métodos básicos (save, findById, delete)
}