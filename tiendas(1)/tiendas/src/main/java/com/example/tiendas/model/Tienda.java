package com.example.tiendas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "tiendas")
public class Tienda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank (message = "El nombre de la tienda debe ser obligatorio")
    @Size(min = 3, max = 50 , message = "El nombre de la tienda debe tener entre 3 y 50 caracteres")
    @Column(nullable = false, length = 50)
    private String nombre;

    @NotBlank (message = "La direccion de la tienda debe ser obligatorio")
    @Size(min = 10, max = 65, message = "La direccion de la tienda debe tener entre 10 y 65 caracteres")
    @Column(nullable = false, length = 65)
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "id_dueño")
    private Dueno dueno;

    @ManyToOne
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;

}
