package com.vetnova.usuarios.service;

import com.vetnova.usuarios.dto.*;
import java.util.Set;

public interface IUsuarioService {
    UsuarioResponseDTO registrarUsuario(UsuarioRequestDTO request);
    UsuarioResponseDTO actualizarRoles(Long idUsuario, Set<Long> idsRoles);
    JwtResponseDTO login(LoginRequestDTO request);
    void invalidarTokenSimulado(String token);
    void desactivarUsuarioLogico(Long idUsuario);
}