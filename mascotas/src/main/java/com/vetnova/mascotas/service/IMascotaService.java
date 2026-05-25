package com.vetnova.mascotas.service;

import com.vetnova.mascotas.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMascotaService {
    MascotaResponseDTO registrar(MascotaRequestDTO request);
    MascotaResponseDTO actualizar(Long id, MascotaRequestDTO request);
    MascotaResponseDTO obtenerPorId(Long id);
    Page<MascotaResponseDTO> buscarPacientes(String filtro, Pageable pageable);
    void transferirPropietario(Long idMascota, TransferenciaRequestDTO request);
    void actualizarEstadoVital(Long idMascota, String nuevoEstado);
}
