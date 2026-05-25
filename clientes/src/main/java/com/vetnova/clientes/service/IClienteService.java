package com.vetnova.clientes.service;

import com.vetnova.clientes.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IClienteService {
    ClienteResponseDTO registrar(ClienteRequestDTO request);
    ClienteResponseDTO actualizarDatos(Long id, ClienteRequestDTO request);
    Page<ClienteResponseDTO> buscarClientes(String textoBusqueda, Pageable pageable);
    ClienteResponseDTO obtenerDetalleCliente(Long id);
    void eliminar(Long id);
}