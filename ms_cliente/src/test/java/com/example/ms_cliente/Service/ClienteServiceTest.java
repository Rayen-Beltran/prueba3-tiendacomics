package com.example.ms_cliente.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ms_cliente.DTO.ClienteDTO;
import com.example.ms_cliente.model.Cliente;
import com.example.ms_cliente.repository.ClienteRepository;
import com.example.ms_cliente.service.ClienteService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private final Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ────────────────────────────────────────────────────
    // obtenerTodos
    // ────────────────────────────────────────────────────

    @Test
    @DisplayName("obtenerTodos: Retorna lista con clientes")
    void obtenerTodos_conDatos() {
        // Given
        Cliente c1 = crearClienteFaker(1);
        Cliente c2 = crearClienteFaker(2);
        when(clienteRepository.findAll()).thenReturn(List.of(c1, c2));

        // When
        List<ClienteDTO> resultado = clienteService.obtenerTodos();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(c1.getNombre(), resultado.get(0).getNombre());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("obtenerTodos: Retorna lista vacía")
    void obtenerTodos_sinDatos() {
        // Given
        when(clienteRepository.findAll()).thenReturn(List.of());

        // When
        List<ClienteDTO> resultado = clienteService.obtenerTodos();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // ────────────────────────────────────────────────────
    // buscarPorId
    // ────────────────────────────────────────────────────

    @Test
    @DisplayName("buscarPorId: Retorna cliente si existe")
    void buscarPorId_existe() {
        // Given
        Cliente cliente = crearClienteFaker(1);
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));

        // When
        ClienteDTO resultado = clienteService.buscarPorId(1);

        // Then
        assertNotNull(resultado);
        assertEquals(cliente.getNombre(), resultado.getNombre());
        assertEquals(cliente.getRut(), resultado.getRut());
        verify(clienteRepository).findById(1);
    }

    @Test
    @DisplayName("buscarPorId: Lanza error si no existe")
    void buscarPorId_noExiste() {
        // Given
        when(clienteRepository.findById(99)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> clienteService.buscarPorId(99));
        assertTrue(ex.getMessage().contains("99"));
        verify(clienteRepository).findById(99);
    }

    // ────────────────────────────────────────────────────
    // guardarCliente
    // ────────────────────────────────────────────────────

    @Test
    @DisplayName("guardarCliente: Persiste y asigna ID")
    void guardarCliente_exito() {
        // Given
        Cliente cliente = crearClienteFaker(null);
        Cliente clienteGuardado = crearClienteFaker(1);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteGuardado);

        // When
        Cliente resultado = clienteService.guardarCliente(cliente);

        // Then
        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        verify(clienteRepository, times(1)).save(cliente);
    }

    // ────────────────────────────────────────────────────
    // actualizarclientes
    // ────────────────────────────────────────────────────

    @Test
    @DisplayName("actualizar: Modifica solo campos enviados")
    void actualizar_exito() {
        // Given
        Cliente clienteExistente = crearClienteFaker(1);
        String nombreOriginal = clienteExistente.getNombre();

        Cliente datosActualizados = new Cliente();
        datosActualizados.setCorreo("nuevo@correo.cl");

        when(clienteRepository.findById(1)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(inv -> inv.getArgument(0));

        // When
        Cliente resultado = clienteService.actualizarclientes(1, datosActualizados);

        // Then
        assertEquals(nombreOriginal, resultado.getNombre());
        assertEquals("nuevo@correo.cl", resultado.getCorreo());
        verify(clienteRepository).save(clienteExistente);
    }

    @Test
    @DisplayName("actualizar: Lanza error si cliente no existe")
    void actualizar_noExiste() {
        // Given
        when(clienteRepository.findById(99)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class,
                () -> clienteService.actualizarclientes(99, new Cliente()));
    }

    // ────────────────────────────────────────────────────
    // eliminarcliente
    // ────────────────────────────────────────────────────

    @Test
    @DisplayName("eliminar: Borra y confirma con mensaje")
    void eliminar_exito() {
        // Given
        Cliente cliente = crearClienteFaker(1);
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        doNothing().when(clienteRepository).delete(cliente);

        // When
        String resultado = clienteService.eliminarcliente(1);

        // Then
        assertTrue(resultado.contains("exitosamente"));
        verify(clienteRepository).delete(cliente);
    }

    @Test
    @DisplayName("eliminar: Avisa error si no existe")
    void eliminar_noExiste() {
        // Given
        when(clienteRepository.findById(99)).thenReturn(Optional.empty());

        // When
        String resultado = clienteService.eliminarcliente(99);

        // Then
        assertNotNull(resultado);
        assertFalse(resultado.contains("exitosamente"));
        verify(clienteRepository, never()).delete(any());
    }

    // ────────────────────────────────────────────────────
    // buscarPorRut
    // ────────────────────────────────────────────────────

    @Test
    @DisplayName("buscarPorRut: Retorna datos del RUT")
    void buscarPorRut_exito() {
        // Given
        Cliente cliente = crearClienteFaker(1);
        when(clienteRepository.findByRut(cliente.getRut())).thenReturn(cliente);

        // When
        List<ClienteDTO> resultado = clienteService.buscarPorRut(cliente.getRut());

        // Then
        assertFalse(resultado.isEmpty());
        assertEquals(cliente.getRut(), resultado.get(0).getRut());
    }

    @Test
    @DisplayName("buscarPorRut: Retorna vacío si no existe")
    void buscarPorRut_noExiste() {
        // Given
        when(clienteRepository.findByRut(anyInt())).thenReturn(null);

        // When
        List<ClienteDTO> resultado = clienteService.buscarPorRut(12345678);

        // Then
        assertTrue(resultado.isEmpty());
    }

    // ────────────────────────────────────────────────────
    // Helper
    // ────────────────────────────────────────────────────

    private Cliente crearClienteFaker(Integer id) {
        return Cliente.builder()
                .id(id)
                .nombre(faker.name().firstName())
                .apellido(faker.name().lastName())
                .edad(faker.number().numberBetween(18, 80))
                .rut(faker.number().numberBetween(1000000, 99999999))
                .dv(String.valueOf(faker.number().digit()))
                .correo(faker.internet().emailAddress())
                .telefono(faker.number().numberBetween(100000000, 999999999))
                .direccion(faker.address().streetAddress())
                .build();
    }
}