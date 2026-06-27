package com.example.tiendas.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.example.tiendas.DTO.EmpleadoDTO;
import com.example.tiendas.model.Empleado;
import com.example.tiendas.repository.EmpleadoRepository;
import com.example.tiendas.service.EmpleadoService;

import net.datafaker.Faker;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas Unitarias: EmpleadoService")
class EmpleadoServiceTest {


     @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private EmpleadoService empleadoService;

    private final Faker faker = new Faker();

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Obtener Todos")
    void obtenerTodos_conDatos() {
        Empleado em1 = createEmpleadoFaker(1);
        Empleado em2 = createEmpleadoFaker(2);
        when(empleadoRepository.findAll()).thenReturn(List.of(em1, em2));

        List<EmpleadoDTO> resultado = empleadoService.obtenerTodos();

        assertEquals(2, resultado.size());
        verify(empleadoRepository).findAll();
    }

    @Test
    @DisplayName("buscarPorId: Retorna DTO si el empleado existe")
    void buscarPorId_existente() {
        Empleado empleado = createEmpleadoFaker(1);
        when(empleadoRepository.findById(1)).thenReturn(Optional.of(empleado));

        EmpleadoDTO resultado = empleadoService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(empleado.getNombre(), resultado.getNombre());
    }

    @Test
    @DisplayName("buscarPorId: Lanza excepción si el empleado no existe")
    void buscarPorId_noExistente() {
        when(empleadoRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> empleadoService.buscarPorId(99));
    }

    
    @Test
    @DisplayName("guardarEmpleado: Persiste y retorna el nuevo Empleado")
    void guardarEmpleado_exito() {
        Empleado empleado = createEmpleadoFaker(null);
        Empleado guardado = createEmpleadoFaker(1);
        when(empleadoRepository.save(any(Empleado.class))).thenReturn(guardado);

        Empleado resultado = empleadoService.guardarEmpleado(empleado);

        assertNotNull(resultado.getId());
        verify(empleadoRepository).save(empleado);
    }

    @Test
    @DisplayName("actualizarEmpleado")
    void actualizarEmpleado_exito() {
        Empleado empleadoExistente = createEmpleadoFaker(1);
        String nombreOriginal = empleadoExistente.getNombre();

        Empleado actualizacion = Empleado.builder()
                .edad(21)
                .correo("CarlosMorales1@gmail.com")
                .telefono(984403450)
                .build();

        when(empleadoRepository.findById(1)).thenReturn(Optional.of(empleadoExistente));
        when(empleadoRepository.save(any(Empleado.class))).thenAnswer(inv -> inv.getArgument(0));

        Empleado resultado = empleadoService.actualizarEmpleado(1, actualizacion);

        assertEquals(21 ,resultado.getEdad());
        assertEquals("CarlosMorales1@gmail.com", resultado.getCorreo());
        assertEquals(984403450, resultado.getTelefono());
    }

    @Test
    @DisplayName("eliminarEmpleado: Borra el empleado y confirma con un mensaje")
    void eliminarEmpleado_exito() {
        Empleado empleado = createEmpleadoFaker(1);
        when(empleadoRepository.findById(1)).thenReturn(Optional.of(empleado));
        doNothing().when(empleadoRepository).delete(empleado);

        String resultado = empleadoService.eliminarempleado(1);

        assertTrue(resultado.contains("exitosamente"));
        verify(empleadoRepository).delete(empleado);
    }


    private Empleado createEmpleadoFaker(Integer id){
        return Empleado.builder()
                    .id(id)
                    .nombre(faker.name().firstName())
                    .apellido(faker.name().lastName())
                    .edad(faker.number().numberBetween(18, 80))
                    .rut(faker.number().numberBetween(1000000, 99999999))
                    .dv(String.valueOf(faker.number().digit()))
                    .correo(faker.internet().emailAddress())
                    .telefono(faker.number().numberBetween(100000000, 999999999))
                    .build();

    }

} 

