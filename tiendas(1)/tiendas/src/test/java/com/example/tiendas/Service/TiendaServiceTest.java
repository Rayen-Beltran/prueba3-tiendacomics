package com.example.tiendas.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.tiendas.repository.TiendaRepository;
import com.example.tiendas.service.TiendaService;

import net.datafaker.Faker;



@ExtendWith(MockitoExtension.class)
public class TiendaServiceTest {

   @Mock
   private TiendaRepository tiendaRepository; // Simulamos el acceso a la base de datos
  
   @InjectMocks
   private TiendaService tiendaService; // Inyectamos el Mock anterior dentro del servicio real
   private Faker faker = new Faker(); // Nuestro generador de datos de Star Wars
   @BeforeEach
   void setUp() {
       // Inicializa los componentes de simulación antes de ejecutar cada prueba
       MockitoAnnotations.openMocks(this);
   }


}
