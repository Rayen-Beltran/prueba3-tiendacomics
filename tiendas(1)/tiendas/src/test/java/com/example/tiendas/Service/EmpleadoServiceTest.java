package com.example.tiendas.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.example.tiendas.DTO.EmpleadoDTO;
import com.example.tiendas.model.Empleado;
import com.example.tiendas.repository.EmpleadoRepository;
import com.example.tiendas.service.EmpleadoService;

@SpringBootTest
public class EmpleadoServiceTest {



  @Autowired
  private EmpleadoService empleadoService;



  @MockitoBean
  private EmpleadoRepository empleadoRepository;

    private Empleado createEmpleado(){
    return new Empleado(
      1,
      "Carlos",
      "Miranda",
      20,
      23456876,
      "K",
      "CarlosMiranda@gmail.com",
      985431670
    );
    }

    @Test
    public void testFindAll() {
        when(empleadoRepository.findAll()).thenReturn(List.of(createEmpleado()));
        List<EmpleadoDTO> empleados = empleadoService.obtenerTodos();
        assertNotNull(empleados);
        assertEquals(1, empleados.size());
    }

    @Test
    public void testFindById() {
        when(empleadoRepository.findById(1)).thenReturn(java.util.Optional.of(createEmpleado()));
        EmpleadoDTO empleado = empleadoService.buscarPorId(1);
        assertNotNull(empleado);
        assertEquals("Juan Perez", empleado.getNombre(), empleado.getApellido());
    }

    @Test
    public void testSave() {
        Empleado empleado = createEmpleado();
        when(empleadoRepository.save(empleado)).thenReturn(empleado);
        Empleado savedEmpleado = empleadoService.guardarEmpleado(empleado);
        assertNotNull(savedEmpleado);
        assertEquals("Carlos Miranda", savedEmpleado.getNombre(), savedEmpleado.getApellido());
    }

    @Test
    public void testPatchEstudiante() {
        Empleado existingEmpleado = createEmpleado();
        Empleado patchData = new Empleado();
        patchData.setNombre("Carlos Actualizado");

        when(empleadoRepository.findById(1)).thenReturn(java.util.Optional.of(existingEmpleado));
        when(empleadoRepository.save(any(Empleado.class))).thenReturn(existingEmpleado);

        Empleado patchedEmpleado = empleadoService.actualizarEmpleado(1, patchData);
        assertNotNull(patchedEmpleado);
        assertEquals("Carlos Actualizado", patchedEmpleado.getNombre());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(empleadoRepository).deleteById(1);
        empleadoService.eliminarempleado(1);
        verify(empleadoRepository, times(1)).deleteById(1);
    }

 

}