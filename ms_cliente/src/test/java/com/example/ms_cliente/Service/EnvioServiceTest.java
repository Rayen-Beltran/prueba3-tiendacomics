package com.example.ms_cliente.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
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

import com.example.ms_cliente.DTO.EnvioDTO;
import com.example.ms_cliente.model.Envio;
import com.example.ms_cliente.repository.EnvioRepository;
import com.example.ms_cliente.service.EnvioService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas Unitarias: EnvioService")
class EnvioServiceTest {

    @Mock
    private EnvioRepository envioRepository;

    @InjectMocks
    private EnvioService envioService;

    private final Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("obtenerTodos: Retorna lista con todos los envíos")
    void obtenerTodos_conDatos() {
        // Given
        Envio e1 = crearEnvioFaker(1);
        Envio e2 = crearEnvioFaker(2);
        when(envioRepository.findAll()).thenReturn(List.of(e1, e2));

        // When
        List<EnvioDTO> resultado = envioService.obtenerTodos();

        // Then
        assertEquals(2, resultado.size());
        verify(envioRepository).findAll();
    }

    @Test
    @DisplayName("buscarPorId: Retorna DTO si el envío existe")
    void buscarPorId_existe() {
        // Given
        Envio envio = crearEnvioFaker(1);
        when(envioRepository.findById(1)).thenReturn(Optional.of(envio));

        // When
        EnvioDTO resultado = envioService.buscarPorId(1);

        // Then
        assertNotNull(resultado);
        assertEquals(envio.getTipoEnvio(), resultado.getTipoEnvio());
    }

    @Test
    @DisplayName("buscarPorId: Lanza excepción si el envío no existe")
    void buscarPorId_noExiste() {
        // Given
        when(envioRepository.findById(99)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> envioService.buscarPorId(99));
    }

    @Test
    @DisplayName("guardarEnvio: Persiste y retorna el nuevo envío")
    void guardarEnvio_exito() {
        // Given
        Envio envio = crearEnvioFaker(null);
        Envio guardado = crearEnvioFaker(1);
        when(envioRepository.save(any(Envio.class))).thenReturn(guardado);

        // When
        Envio resultado = envioService.guardarEnvio(envio);

        // Then
        assertNotNull(resultado.getIdEnvio());
        verify(envioRepository).save(envio);
    }

    @Test
    @DisplayName("actualizarEnvio: Modifica solo los campos enviados")
    void actualizarEnvio_exito() {
        // Given
        Envio existente = crearEnvioFaker(1);
        LocalDate fechaOriginal = existente.getFechaSalida();

        Envio actualizacion = Envio.builder()
                .tipoEnvio("Express")
                .sucursal("Santiago")
                .build();

        when(envioRepository.findById(1)).thenReturn(Optional.of(existente));
        when(envioRepository.save(any(Envio.class))).thenAnswer(inv -> inv.getArgument(0));

        // When
        Envio resultado = envioService.actualizarEnvio(1, actualizacion);

        // Then
        assertEquals("Express", resultado.getTipoEnvio());
        assertEquals("Santiago", resultado.getSucursal());
        assertEquals(fechaOriginal, resultado.getFechaSalida()); // fecha sin cambio
    }

    @Test
    @DisplayName("eliminarEnvio: Borra el envío y confirma con mensaje")
    void eliminarEnvio_exito() {
        // Given
        Envio envio = crearEnvioFaker(1);
        when(envioRepository.findById(1)).thenReturn(Optional.of(envio));
        doNothing().when(envioRepository).delete(envio);

        // When
        String resultado = envioService.eliminarEnvio(1);

        // Then
        assertTrue(resultado.contains("exitosamente"));
        verify(envioRepository).delete(envio);
    }

    private Envio crearEnvioFaker(Integer id) {
        return Envio.builder()
                .idEnvio(id)
                .fechaSalida(LocalDate.now())
                .fechaEntrega(LocalDate.now().plusDays(3))
                .tipoEnvio("Normal")
                .sucursal("Valparaíso")
                .build();
    }
}