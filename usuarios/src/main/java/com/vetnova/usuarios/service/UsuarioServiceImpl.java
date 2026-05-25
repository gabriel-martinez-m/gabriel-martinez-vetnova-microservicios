package com.vetnova.usuarios.service;

import com.vetnova.usuarios.dto.*;
import com.vetnova.usuarios.exception.ResourceNotFoundException;
import com.vetnova.usuarios.model.*;
import com.vetnova.usuarios.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    // Inyección simulada de Feign Client
    // private final NotificacionFeignClient notificacionFeignClient;

    @Override
    @Transactional
    public UsuarioResponseDTO registrarUsuario(UsuarioRequestDTO request) {
        log.info("HU-UR01: Iniciando registro de usuario con email: {}", request.getEmail());

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            log.warn("Intento de registro con email ya existente: {}", request.getEmail());
            throw new IllegalArgumentException("El email ya se encuentra registrado");
        }

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .password("$2a$10$SimulatedBcryptHash...") // Simulación de cifrado profesional
                .estado("PENDIENTE_ACTIVACION")
                .idSucursal(request.getIdSucursal())
                .rolesAsignados(new ArrayList<>())
                .build();

        // Mapear los roles intermedios
        List<UsuarioRol> rolesAsignados = request.getIdsRoles().stream().map(idRol -> {
            Rol rol = rolRepository.findById(idRol)
                .orElseThrow(() -> new ResourceNotFoundException("Rol con ID " + idRol + " no encontrado"));
            return UsuarioRol.builder()
                    .usuario(usuario)
                    .rol(rol)
                    .fechaAsignacion(LocalDateTime.now())
                    .build();
        }).collect(Collectors.toList());

        usuario.getRolesAsignados().addAll(rolesAsignados);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        /* HU-UR01 - Bloque Feign Client Comentado temporalmente:
        try {
            notificacionFeignClient.enviarCorreoBienvenida(usuarioGuardado.getEmail());
            log.info("Evento Feign enviado con éxito a notificaciones-service");
        } catch(Exception e) {
            log.error("Fallo de comunicación con notificaciones-service: {}", e.getMessage());
        }
        */

        log.info("Usuario creado exitosamente con ID: {}", usuarioGuardado.getIdUsuario());
        return mapToResponseDTO(usuarioGuardado);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO actualizarRoles(Long idUsuario, Set<Long> idsRoles) {
        log.info("HU-UR02: Modificando roles para usuario ID: {}", idUsuario);
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        usuario.getRolesAsignados().clear();

        List<UsuarioRol> nuevosRoles = idsRoles.stream().map(idRol -> {
            Rol rol = rolRepository.findById(idRol)
                .orElseThrow(() -> new ResourceNotFoundException("Rol con ID " + idRol + " no encontrado"));
            return UsuarioRol.builder()
                    .usuario(usuario)
                    .rol(rol)
                    .fechaAsignacion(LocalDateTime.now())
                    .build();
        }).collect(Collectors.toList());

        usuario.getRolesAsignados().addAll(nuevosRoles);
        return mapToResponseDTO(usuarioRepository.save(usuario));
    }

    @Override
    @Transactional(readOnly = true)
    public JwtResponseDTO login(LoginRequestDTO request) {
        log.info("HU-UR03: Solicitud de login para: {}", request.getEmail());
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Credenciales inválidas"));

        if (!usuario.getEstado().equals("ACTIVE") && !usuario.getEstado().equals("PENDIENTE_ACTIVACION")) {
            log.warn("Intento de login de usuario inactivo: {}", request.getEmail());
            throw new IllegalArgumentException("La cuenta está desactivada");
        }

        log.info("Login exitoso. Generando token mock de 8 horas.");
        return new JwtResponseDTO("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.MockTokenGeneratedByVetNova...", "Bearer", usuario.getEmail(), 28800L);
    }

    @Override
    public void invalidarTokenSimulado(String token) {
        log.info("HU-UR04: Token '{}' enviado a la lista negra (Redis Mock) con éxito.", token);
    }

    @Override
    @Transactional
    public void desactivarUsuarioLogico(Long idUsuario) {
        log.info("HU-UR05: Solicitud de baja lógica para usuario ID: {}", idUsuario);
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        usuario.setEstado("INACTIVE");
        usuarioRepository.save(usuario);
        log.info("Usuario ID: {} cambiado a estado INACTIVE correctamente.", idUsuario);
    }

    private UsuarioResponseDTO mapToResponseDTO(Usuario usuario) {
        List<String> nombresRoles = usuario.getRolesAsignados().stream()
                .map(ur -> ur.getRol().getNombreRol())
                .collect(Collectors.toList());

        return UsuarioResponseDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .estado(usuario.getEstado())
                .idSucursal(usuario.getIdSucursal())
                .roles(nombresRoles)
                .build();
    }
}