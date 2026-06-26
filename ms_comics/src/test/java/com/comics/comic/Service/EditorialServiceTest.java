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

import com.comics.comic.model.Editorial;
import com.comics.comic.repository.EditorialRepository;
import com.comics.comic.service.EditorialService;

@ExtendWith(MockitoExtension.class)
public class EditorialServiceTest {
    
    @Mock
    private EditorialRepository editorialRepository;

    @InjectMocks
    private EditorialService editorialService;

    private Faker faker;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    public void testGuardarEditorial_Exitoso() {

        Integer idSimulado = 1;

        String nombreAleatorio = faker.book().publisher();

        Editorial editorialSimulada = new Editorial();
        editorialSimulada.setId(idSimulado);
        editorialSimulada.setNombre(nombreAleatorio);

        when(editorialRepository.save(editorialSimulada)).thenReturn(editorialSimulada);

        Editorial resultado = editorialService.guardarEditorial(editorialSimulada);

        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals(nombreAleatorio, resultado.getNombre(), "El nombre de la editorial debe coincidir con el valor simulado");

        verify(editorialRepository, times(1)).save(editorialSimulada);
    }
}
