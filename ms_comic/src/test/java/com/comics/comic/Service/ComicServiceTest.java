package com.comics.comic.Service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;

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
        Integer idSimulado = 13;

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

        ComicDTO comicDTO = comicService.findById(idSimulado);

        assertNotNull("El cómic no debe ser nulo", comicDTO);
        assertEquals("El ID del cómic no coincide", idSimulado, comicDTO.getId_comic());

        Verify(comicRepository, times(1)).findById(idSimulado);
    }
}