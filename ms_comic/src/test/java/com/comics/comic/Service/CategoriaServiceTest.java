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

import com.comics.comic.model.Categoria;
import com.comics.comic.repository.CategoriaRepository;
import com.comics.comic.service.CategoriaService;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {
    
    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    private Faker faker;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    public void testGuardarCategoria_Exitoso() {

        String nombreAleatorio = faker.book().genre();

        Categoria categoriaSimulada = new Categoria();
        categoriaSimulada.setNombre(nombreAleatorio);

        when(categoriaRepository.save(categoriaSimulada)).thenReturn(categoriaSimulada);

        Categoria resultado = categoriaService.guardarCategoria(categoriaSimulada);

        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals(nombreAleatorio, resultado.getNombre(), "El nombre de la categoría debe coincidir con el valor simulado");

        verify(categoriaRepository, times(1)).save(categoriaSimulada);
    }
}
