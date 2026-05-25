package com.vetnova.mascotas.controller;

import com.vetnova.mascotas.dto.*;
import com.vetnova.mascotas.service.IMascotaService;
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
@RequestMapping("/api/v1/mascotas")
@RequiredArgsConstructor
public class MascotaController {

    private final IMascotaService mascotaService;

    @PostMapping
    public ResponseEntity<MascotaResponseDTO> registrarMascota(@Valid @RequestBody MascotaRequestDTO dto) {
        return new ResponseEntity<>(mascotaService.registrar(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MascotaResponseDTO> actualizarMascota(
            @PathVariable Long id,
            @Valid @RequestBody MascotaRequestDTO dto) {
        return ResponseEntity.ok(mascotaService.actualizar(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaResponseDTO> obtenerFichaClinica(@PathVariable Long id) {
        // Resuelve HU-MA04: Visualización de ficha y constantes biológicas calculadas
        return ResponseEntity.ok(mascotaService.obtenerPorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<MascotaResponseDTO>> buscarPacientes(
            @RequestParam String termino,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombre").ascending());
        return ResponseEntity.ok(mascotaService.buscarPacientes(termino, pageable));
    }

    @PatchMapping("/{id}/transferir")
    public ResponseEntity<Void> transferirPropietario(
            @PathVariable Long id,
            @Valid @RequestBody TransferenciaRequestDTO dto) {
        mascotaService.transferirPropietario(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estado-vital")
    public ResponseEntity<Void> actualizarEstadoVital(
            @PathVariable Long id,
            @RequestParam String nuevoEstado) {
        mascotaService.actualizarEstadoVital(id, nuevoEstado);
        return ResponseEntity.noContent().build();
    }
}