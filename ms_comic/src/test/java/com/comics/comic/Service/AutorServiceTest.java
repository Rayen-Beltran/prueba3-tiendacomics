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

import com.comics.comic.model.Autor;
import com.comics.comic.repository.AutorRepository;
import com.comics.comic.service.AutorService;



@ExtendWith(MockitoExtension.class)
public class AutorServiceTest {
    
    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private AutorService autorService;

    private Faker faker;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    public void testGuardarAutor_Exitoso() {

        Integer idSimulado = 1;

        String nombreAleatorio = faker.book().author();

        Autor autorSimulado = new Autor();
        autorSimulado.setId(idSimulado);
        autorSimulado.setNombre(nombreAleatorio);

        when(autorRepository.save(autorSimulado)).thenReturn(autorSimulado);

        Autor resultado = autorService.guardarAutor(autorSimulado);

        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals(nombreAleatorio, resultado.getNombre(), "El nombre del autor debe coincidir con el valor simulado");

        verify(autorRepository, times(1)).save(autorSimulado);
    }
}
