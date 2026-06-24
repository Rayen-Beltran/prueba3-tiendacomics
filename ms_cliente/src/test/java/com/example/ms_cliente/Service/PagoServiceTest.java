package com.example.ms_cliente.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ms_cliente.DTO.PagoDTO;
import com.example.ms_cliente.model.Pago;
import com.example.ms_cliente.repository.PagoRepository;
import com.example.ms_cliente.service.PagoService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas Unitarias: PagoService")
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @InjectMocks
    private PagoService pagoService;

    private final Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("buscarPorId: Retorna DTO si el pago existe")
    void buscarPorId_existe() {
        // Given
        Pago pago = crearPagoFaker(1);
        when(pagoRepository.findById(1)).thenReturn(Optional.of(pago));

        // When
        PagoDTO resultado = pagoService.buscarPorId(1);

        // Then
        assertNotNull(resultado);
        assertEquals(pago.getDescripcion(), resultado.getDescripcion());
        assertEquals(pago.getMonto_total(), resultado.getMonto_total());
    }

    @Test
    @DisplayName("buscarPorId: Lanza excepción si el pago no existe")
    void buscarPorId_noExiste() {
        // Given
        when(pagoRepository.findById(99)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> pagoService.buscarPorId(99));
    }

    @Test
    @DisplayName("guardarPago: Persiste y retorna el nuevo pago")
    void guardarPago_exito() {
        // Given
        Pago pago = crearPagoFaker(null);
        Pago guardado = crearPagoFaker(1);
        when(pagoRepository.save(any(Pago.class))).thenReturn(guardado);

        // When
        Pago resultado = pagoService.guardarPago(pago);

        // Then
        assertNotNull(resultado.getId_pago());
        verify(pagoRepository).save(pago);
    }

    @Test
    @DisplayName("actualizarPago: Modifica el monto y la descripción")
    void actualizarPago_exito() {
        // Given
        Pago pagoExistente = crearPagoFaker(1);
        Pago actualizado = Pago.builder().descripcion("Nueva descripción").Monto_total(9990).build();
        when(pagoRepository.findById(1)).thenReturn(Optional.of(pagoExistente));
        when(pagoRepository.save(any(Pago.class))).thenAnswer(inv -> inv.getArgument(0));

        // When
        Pago resultado = pagoService.actualizarPago(1, actualizado);

        // Then
        assertEquals("Nueva descripción", resultado.getDescripcion());
        assertEquals(9990, resultado.getMonto_total());
    }

    @Test
    @DisplayName("eliminar: Borra el pago y confirma con mensaje")
    void eliminar_exito() {
        // Given
        Pago pago = crearPagoFaker(1);
        when(pagoRepository.findById(1)).thenReturn(Optional.of(pago));
        doNothing().when(pagoRepository).delete(pago);

        // When
        String resultado = pagoService.eliminar(1);

        // Then
        assertTrue(resultado.contains("exitosamente"));
        verify(pagoRepository).delete(pago);
    }

    @Test
    @DisplayName("eliminar: Retorna error si el pago no existe")
    void eliminar_noExiste() {
        // Given
        when(pagoRepository.findById(99)).thenReturn(Optional.empty());

        // When
        String resultado = pagoService.eliminar(99);

        // Then
        assertFalse(resultado.contains("exitosamente"));
        verify(pagoRepository, never()).delete(any());
    }

    private Pago crearPagoFaker(Integer id) {
        return Pago.builder()
                .id_pago(id)
                .descripcion(faker.commerce().productName())
                .Monto_total(faker.number().numberBetween(1000, 100000))
                .build();
    }
}