package com.example.tiendas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor
@Builder
@Entity
@Table(name = "duenos")
public class Dueno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre del dueño es obligatorio")
    @Size(min = 3, max = 15, message = "El nombre tiene que tener entre 3 y 15 caracteres")
    @Column(nullable = false, length = 15)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 3, max = 20, message = "El apellido tiene que tener entre 3 y 20 caracteres")
    @Column(nullable = false, length = 20)
    private String apellido;

    @NotNull(message = "El rut es obligatorio")
    @Size(min = 8, max = 8, message = "El rut tiene que tener 8 caracter")
    @Column(nullable = false, length = 8)
    private Integer rut;

    @NotBlank(message = "El dv es obligatorio")
    @Size(min = 1, max = 1, message = "El dv tiene que tener 1 caracter")
    @Column(nullable = false, length = 1)
    private String dv;
}
