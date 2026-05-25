package com.vetnova.usuarios.dto;

import lombok.*;
import java.util.List;

@Getter @Setter
@Builder
@AllArgsConstructor
public class UsuarioResponseDTO {
    private Long idUsuario;
    private String nombre;
    private String email;
    private String estado;
    private Long idSucursal;
    private List<String> roles;
}