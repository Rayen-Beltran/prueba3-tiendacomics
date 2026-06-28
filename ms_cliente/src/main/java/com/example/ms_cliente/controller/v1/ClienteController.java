package com.example.ms_cliente.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ms_cliente.DTO.ClienteDTO;
import com.example.ms_cliente.DTO.EnvioDTO;
import com.example.ms_cliente.model.Cliente;
import com.example.ms_cliente.service.ClienteService;
import com.example.ms_cliente.service.EnvioClientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "Gestión de clientes de la tienda de comics")
public class ClienteController {

    @Autowired
    private EnvioClientService envioClientService;

    @Operation(summary = "Obtener envíos disponibles via WebClient")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de envíos obtenida"),
        @ApiResponse(responseCode = "503", description = "Servicio de envíos no disponible")
    })
    @GetMapping("/envios")
    public ResponseEntity<?> obtenerEnviosDeClientes() {
        try {
            List<EnvioDTO> envios = envioClientService.obtenerEnvios();
            return ResponseEntity.ok(envios);
        } catch (RuntimeException e) {
            log.error("Error al obtener envíos: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("No se pudo obtener envíos: " + e.getMessage());
        }
    }

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Listar todos los clientes")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida"),
        @ApiResponse(responseCode = "204", description = "No hay clientes registrados")
    })
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> todosLosClientes() {
        log.info("Obteniendo todos los clientes");
        List<ClienteDTO> clientes = clienteService.obtenerTodos();
        if (clientes.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @Operation(summary = "Buscar cliente por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(
            @Parameter(description = "ID del cliente") @PathVariable Integer id) {
        log.info("Buscando cliente con ID: {}", id);
        try {
            return new ResponseEntity<>(clienteService.buscarPorId(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al buscar cliente con ID: {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Buscar cliente por RUT")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "204", description = "No se encontró cliente con ese RUT")
    })
    @GetMapping("/rut/{rut}")
    public ResponseEntity<ClienteDTO> buscarPorRut(
            @Parameter(description = "RUT del cliente sin puntos ni guión") @PathVariable Integer rut) {
        log.info("Buscando cliente con RUT: {}", rut);
        List<ClienteDTO> clientes = clienteService.buscarPorRut(rut);
        if (clientes.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(clientes.get(0), HttpStatus.OK);
    }

    @Operation(summary = "Agregar un nuevo cliente")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Cliente> agregarCliente(@Valid @RequestBody Cliente cliente) {
        log.info("Agregando nuevo cliente: {}", cliente);
        try {
            return new ResponseEntity<>(clienteService.guardarCliente(cliente), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error al agregar cliente: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Actualizar campos específicos del cliente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente actualizado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Cliente> editarCliente(
            @Parameter(description = "ID del cliente") @PathVariable Integer id,
            @Valid @RequestBody Cliente cliente) {
        log.info("Editando cliente con ID: {}", id);
        try {
            return new ResponseEntity<>(clienteService.actualizarclientes(id, cliente), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al editar cliente con ID: {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Actualizar cliente completo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente actualizado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(
            @Parameter(description = "ID del cliente") @PathVariable Integer id,
            @Valid @RequestBody Cliente cliente) {
        log.info("Actualizando cliente con ID: {}", id);
        try {
            return new ResponseEntity<>(clienteService.actualizarclientes(id, cliente), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al actualizar cliente con ID: {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Eliminar cliente por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCliente(
            @Parameter(description = "ID del cliente") @PathVariable Integer id) {
        log.info("Eliminando cliente con ID: {}", id);
        String resultado = clienteService.eliminarcliente(id);
        if (resultado.contains("exitosamente")) return new ResponseEntity<>(resultado, HttpStatus.OK);
        return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
    }
}