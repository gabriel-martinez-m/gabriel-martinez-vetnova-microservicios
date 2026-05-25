package com.vetnova.clientes.repository;

import com.vetnova.clientes.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByRut(String rut);
    boolean existsByRut(String rut);

    @Query("SELECT c FROM Cliente c WHERE " +
           "(LOWER(c.rut) LIKE LOWER(CONCAT('%', :txt, '%')) OR " +
           " LOWER(c.nombre) LIKE LOWER(CONCAT('%', :txt, '%')) OR " +
           " c.telefono LIKE CONCAT('%', :txt, '%')) AND c.estado != 'INACTIVO'")
    Page<Cliente> buscarPorFiltros(@Param("txt") String textoBusqueda, Pageable pageable);
}