package com.vetnova.clientes.controller;

import com.vetnova.clientes.dto.*;
import com.vetnova.clientes.service.IClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final IClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> registrarCliente(@Valid @RequestBody ClienteRequestDTO dto) {
        return new ResponseEntity<>(clienteService.registrar(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizarCliente(
            @PathVariable Long id, 
            @Valid @RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.ok(clienteService.actualizarDatos(id, dto));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<ClienteResponseDTO>> buscarClientes(
            @RequestParam String termino,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // Ordenado alfabéticamente según el nuevo campo de la entidad 'nombre'
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombre").ascending());
        return ResponseEntity.ok(clienteService.buscarClientes(termino, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> obtenerDetalleCompleto(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.obtenerDetalleCliente(id));
    }

    @DeleteMapping("/{id}/anonimizar")
    public ResponseEntity<Void> anonimizarClienteLegales(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}