package com.comics.comic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name= "Tienda")
public class Tienda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_tienda;

    @NotBlank (message = "El nombre de la tienda debe ser obligatorio")
    @Size(min = 3, max = 100, message = "El nombre de la tienda debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre_tienda;

    @NotBlank (message = "La direccion de la tienda debe ser obligatorio")
    @Size(min = 10, max = 200, message = "La direccion de la tienda debe tener entre 10 y 200 caracteres")
    @Column(nullable = false, length = 200)
    private String direccion_tienda;

    public Integer getId_tienda() {
        return id_tienda;
    }
}