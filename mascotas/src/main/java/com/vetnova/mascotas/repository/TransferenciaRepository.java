package com.vetnova.mascotas.repository;

import com.vetnova.mascotas.model.TransferenciaPropietario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferenciaRepository extends JpaRepository<TransferenciaPropietario, Long> {
}