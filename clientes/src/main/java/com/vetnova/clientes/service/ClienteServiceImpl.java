package com.vetnova.clientes.service;

import com.vetnova.clientes.dto.*;
import com.vetnova.clientes.exception.ResourceNotFoundException;
import com.vetnova.clientes.model.Cliente;
import com.vetnova.clientes.model.ContactoEmergencia;
import com.vetnova.clientes.model.HistorialCliente;
import com.vetnova.clientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements IClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    public ClienteResponseDTO registrar(ClienteRequestDTO request) {
        log.info("Registrando cliente con RUT: {}", request.getRut());

        if (clienteRepository.existsByRut(request.getRut())) {
            throw new IllegalArgumentException("El RUT ya se encuentra registrado");
        }

        Cliente cliente = Cliente.builder()
                .rut(request.getRut())
                .nombre(request.getNombre())
                .telefono(request.getTelefono())
                .email(request.getEmail())
                .direccion(request.getDireccion())
                .estado("ACTIVO")
                .fechaCreacion(LocalDateTime.now())
                .build();

        // Inicialización obligatoria del Historial por Composición UML (1 posee 1)
        HistorialCliente historialInicial = HistorialCliente.builder()
                .totalCompras(0)
                .totalAtenciones(0)
                .fechaUltimaAtencion(null)
                .build();
        cliente.asignarHistorial(historialInicial);

        // Procesar contactos de emergencia si se envían en la petición (1 tiene 0..*)
        if (request.getContactos() != null) {
            request.getContactos().forEach(c -> cliente.agregarContacto(
                    ContactoEmergencia.builder()
                            .nombre(c.getNombre())
                            .telefono(c.getTelefono())
                            .parentesco(c.getParentesco())
                            .build()
            ));
        }

        return mapToResponse(clienteRepository.save(cliente));
    }

    @Override
    @Transactional
    public ClienteResponseDTO actualizarDatos(Long id, ClienteRequestDTO request) {
        log.info("Actualizando datos para cliente ID: {}", id);
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        cliente.setNombre(request.getNombre());
        cliente.setTelefono(request.getTelefono());
        cliente.setEmail(request.getEmail());
        cliente.setDireccion(request.getDireccion());
        cliente.setFechaUltimaModificacion(LocalDateTime.now());

        return mapToResponse(clienteRepository.save(cliente));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClienteResponseDTO> buscarClientes(String textoBusqueda, Pageable pageable) {
        log.info("Buscando clientes con filtro: '{}'", textoBusqueda);
        if (textoBusqueda == null || textoBusqueda.trim().length() < 3) {
            throw new IllegalArgumentException("La búsqueda requiere un mínimo de 3 caracteres");
        }
        return clienteRepository.buscarPorFiltros(textoBusqueda, pageable).map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO obtenerDetalleCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        return mapToResponse(cliente);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Procesando baja del cliente con ID: {}", id);
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        String codigoUnico = UUID.randomUUID().toString().substring(0, 8);
        cliente.setNombre("ANON_" + codigoUnico);
        cliente.setRut("ANON_" + codigoUnico);
        cliente.setTelefono("+000000000");
        cliente.setEmail("anonimo_" + codigoUnico + "@vetnova.internal");
        cliente.setDireccion("ANONIMIZADO");
        cliente.setEstado("INACTIVO");
        cliente.setFechaUltimaModificacion(LocalDateTime.now());

        clienteRepository.save(cliente);
        log.info("Cliente ID: {} dado de baja de forma conforme a políticas de privacidad.", id);
    }

    private ClienteResponseDTO mapToResponse(Cliente cliente) {
        HistorialResumenDTO historialDTO = null;
        if (cliente.getHistorialCliente() != null) {
            historialDTO = HistorialResumenDTO.builder()
                    .idHistorial(cliente.getHistorialCliente().getIdHistorial())
                    .totalCompras(cliente.getHistorialCliente().getTotalCompras())
                    .totalAtenciones(cliente.getHistorialCliente().getTotalAtenciones())
                    .fechaUltimaAtencion(cliente.getHistorialCliente().getFechaUltimaAtencion())
                    .build();
        }

        return ClienteResponseDTO.builder()
                .idCliente(cliente.getIdCliente())
                .rut(cliente.getRut())
                .nombre(cliente.getNombre())
                .telefono(cliente.getTelefono())
                .email(cliente.getEmail())
                .direccion(cliente.getDireccion())
                .estado(cliente.getEstado())
                .fechaCreacion(cliente.getFechaCreacion())
                .contactos(cliente.getContactosEmergencia() != null ? 
                        cliente.getContactosEmergencia().stream()
                                .map(c -> ContactoEmergenciaDTO.builder()
                                        .nombre(c.getNombre())
                                        .telefono(c.getTelefono())
                                        .parentesco(c.getParentesco())
                                        .build())
                                .collect(Collectors.toList()) : new ArrayList<>())
                .historial(historialDTO)
                .build();
    }
}