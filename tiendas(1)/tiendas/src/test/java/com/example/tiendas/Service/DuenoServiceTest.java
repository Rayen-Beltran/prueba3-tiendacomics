package com.example.tiendas.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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

import com.example.tiendas.DTO.DuenoDTO;
import com.example.tiendas.model.Dueno;
import com.example.tiendas.repository.DuenoRepository;
import com.example.tiendas.service.DuenoService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas Unitarias:DuenoService")
class DuenoServiceTest {

    @Mock
    private DuenoRepository duenoRepository;

    @InjectMocks
    private DuenoService duenoService;

    private Faker faker = new Faker();
    
    @BeforeEach
    void setUp() {
       MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Obtener Todos")
    void obtenerTodos_conDatos() {
        Dueno d1 = createDuenoFaker(1);
        Dueno d2 = createDuenoFaker(2);
        when(duenoRepository.findAll()).thenReturn(List.of(d1, d2));

        List<DuenoDTO> resultado = duenoService.obtenerTodos();

        assertEquals(2, resultado.size());
        verify(duenoRepository).findAll();
    }

    @Test
    @DisplayName("buscarPorId: Retorna DTO si el Dueño existe")
    void buscarPorId_existente() {
        Dueno dueno = createDuenoFaker(1);
        when(duenoRepository.findById(1)).thenReturn(Optional.of(dueno));

        DuenoDTO resultado = duenoService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(dueno.getNombre(), resultado.getNombre());
    }

    @Test
    @DisplayName("buscarPorId: Lanza excepción si el Dueño no existe")
    void buscarPorId_noExistente() {
        when(duenoRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> duenoService.buscarPorId(99));
    }

    @Test
    @DisplayName("guardarDueno: Persiste y retorna el nuevo Dueño")
    void guardarDueno_exito() {
        Dueno dueno = createDuenoFaker(null);
        Dueno guardado = createDuenoFaker(1);
        when(duenoRepository.save(any(Dueno.class))).thenReturn(guardado);

        Dueno resultado = duenoService.guardarDueno(dueno);

        assertNotNull(resultado.getId());
        verify(duenoRepository).save(dueno);
    }

    @Test
    @DisplayName("actualizarDueno")
    void actualizarDueno_exito() {
        Dueno existente = createDuenoFaker(1);

        Dueno actualizacion = Dueno.builder()
                .nombre("Harol")
                .apellido("Galves")
                .build();

        when(duenoRepository.findById(1)).thenReturn(Optional.of(existente));
        when(duenoRepository.save(any(Dueno.class))).thenAnswer(inv -> inv.getArgument(0));

        Dueno resultado = duenoService.actualizarDueno(1, actualizacion);

        assertEquals("Harol", resultado.getNombre());
        assertEquals("Galves", resultado.getApellido());
    }

    @Test
    @DisplayName("eliminarDueno: Borra la dueño y confirma con un mensaje")
    void eliminarDueno_exito() {
        Dueno dueno = createDuenoFaker(1);
        when(duenoRepository.findById(1)).thenReturn(Optional.of(dueno));
        doNothing().when(duenoRepository).delete(dueno);

        String resultado = duenoService.eliminardueno(null);

        assertTrue(resultado.contains("exitosamente"));
        verify(duenoRepository).delete(dueno);
    }

    private Dueno createDuenoFaker(Integer id){
        return Dueno.builder()
                    .id(id)
                    .nombre(faker.name().firstName())
                    .apellido(faker.name().lastName())
                    .rut(faker.number().numberBetween(1000000, 99999999))
                    .dv(String.valueOf(faker.number().digit()))
                    .build();

    }
}
