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

import com.example.tiendas.DTO.TiendaDTO;
import com.example.tiendas.model.Tienda;
import com.example.tiendas.repository.TiendaRepository;
import com.example.tiendas.service.TiendaService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas Unitarias: TiendaSerive")
class TiendaServiceTest {

   @Mock
   private TiendaRepository tiendaRepository; 
  
   @InjectMocks
   private TiendaService tiendaService; 
   
   private Faker faker = new Faker(); 
   
   @BeforeEach
   void setUp() {
       MockitoAnnotations.openMocks(this);
   }

   @Test
   @DisplayName("Obtener Todos")
   void obtenerTodos_conDatos() {
        Tienda t1 = createTiendaFaker(1);
        Tienda t2 = createTiendaFaker(2);
        when(tiendaRepository.findAll()).thenReturn(List.of(t1, t2));

        List<TiendaDTO> resultado = tiendaService.obtenerTodas();

        assertEquals(2, resultado.size());
        verify(tiendaRepository).findAll();
    }

    @Test
    @DisplayName("buscarPorId: Retorna DTO si la Tienda existe")
    void buscarPorId_existente() {
        Tienda tienda = createTiendaFaker(1);
        when(tiendaRepository.findById(1)).thenReturn(Optional.of(tienda));

        TiendaDTO resultado = tiendaService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(tienda.getNombre(), resultado.getNombre());
    }

    @Test
    @DisplayName("buscarPorId: Lanza excepción si la tienda no existe")
    void buscarPorId_noExistente() {
        when(tiendaRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> tiendaService.buscarPorId(99));
    }

    @Test
    @DisplayName("guardarTienda: Persiste y retorna la nueva Tienda")
    void guardarTienda_exito() {
        Tienda tienda = createTiendaFaker(null);
        Tienda guardado = createTiendaFaker(1);
        when(tiendaRepository.save(any(Tienda.class))).thenReturn(guardado);

        Tienda resultado = tiendaService.guardarTienda(tienda);

        assertNotNull(resultado.getId());
        verify(tiendaRepository).save(tienda);
    }

    @Test
    @DisplayName("actualizarTienda")
    void actualizarTienda_exito() {
        Tienda existente = createTiendaFaker(1);

        Tienda actualizacion = Tienda.builder()
                .nombre("FORISEVEN")
                .direccion("La Farfana 4050")
                .build();

        when(tiendaRepository.findById(1)).thenReturn(Optional.of(existente));
        when(tiendaRepository.save(any(Tienda.class))).thenAnswer(inv -> inv.getArgument(0));

        Tienda resultado = tiendaService.actualizarTienda(1, actualizacion);

        assertEquals("FORISEVEN", resultado.getNombre());
        assertEquals("La Farfana 4050", resultado.getDireccion());
    }

    @Test
    @DisplayName("eliminarTienda: Borra la tienda y confirma con un mensaje")
    void eliminarTienda_exito() {
        Tienda tienda = createTiendaFaker(1);
        when(tiendaRepository.findById(1)).thenReturn(Optional.of(tienda));
        doNothing().when(tiendaRepository).delete(tienda);

        String resultado = tiendaService.eliminarTienda(1);

        assertTrue(resultado.contains("exitosamente"));
        verify(tiendaRepository).delete(tienda);
    }


    private Tienda createTiendaFaker(Integer id){
        return Tienda.builder()
                    .id(id)
                    .nombre(faker.name().firstName())
                    .direccion(faker.address().streetAddress())
                    .build();

    }


}
