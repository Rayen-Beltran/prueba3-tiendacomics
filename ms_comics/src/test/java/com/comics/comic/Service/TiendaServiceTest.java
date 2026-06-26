package com.comics.comic.Service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import net.datafaker.Faker;

import com.comics.comic.model.Tienda;
import com.comics.comic.repository.TiendaRepository;
import com.comics.comic.service.TiendaService;
import com.comics.comic.DTO.TiendaDTO;

@ExtendWith(MockitoExtension.class)
public class TiendaServiceTest {
    
    @Mock
    private TiendaRepository tiendaRepository;

    @InjectMocks
    private TiendaService tiendaService;

    private Faker faker;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    public void testBuscarTienda_Exitoso() {

        Integer idSimulado = 1;

        String nombreAleatorio = faker.company().name();
        Tienda tiendaSimulada = new Tienda();
        tiendaSimulada.setId_tienda(idSimulado);
        tiendaSimulada.setNombre_tienda(nombreAleatorio);

        when(tiendaRepository.findById(idSimulado)).thenReturn(java.util.Optional.of(tiendaSimulada));

        TiendaDTO resultado = tiendaService.findById(idSimulado);

        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals(idSimulado, resultado.getId_tienda(), "El id de la tienda debe coincidir con el valor simulado");

        verify(tiendaRepository, times(1)).findById(idSimulado);
    }
}
