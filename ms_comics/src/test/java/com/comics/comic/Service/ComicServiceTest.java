package com.comics.comic.Service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.comics.comic.model.Comic;
import com.comics.comic.repository.ComicRepository;
import com.comics.comic.service.ComicService;
import com.comics.comic.DTO.ComicDTO;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
public class ComicServiceTest {

    @Mock
    private ComicRepository comicRepository;

    @InjectMocks
    private ComicService comicService;

    private Faker faker;
    @BeforeEach

    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBuscarPorId_Exitoso() {
        Integer idSimulado = 2;

        String nombreAleatorio = faker.book().title();

        Comic comicSimulado = new Comic();
        comicSimulado.setId(idSimulado);
        comicSimulado.setTitulo(nombreAleatorio);
        comicSimulado.setAutores(null);
        comicSimulado.setEditoriales(null);
        comicSimulado.setCategorias(null);
        comicSimulado.setFechaPublicacion(null);
        comicSimulado.setPrecio((double) 12083);
        comicSimulado.setStock(122);
        comicSimulado.setISBN("ada");

        when(comicRepository.findById(idSimulado)).thenReturn(List.of(comicSimulado));

        ComicDTO Resultado = comicService.findById(idSimulado);

        assertNotNull(Resultado, "El resultado no debe ser nulo");
        assertEquals(idSimulado, Resultado.getId(), "El ID del resultado debe coincidir con el ID simulado");

        verify(comicRepository, times(1)).findById(idSimulado);
    }
}