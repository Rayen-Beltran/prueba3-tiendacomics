package com.example.tiendas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "empleados")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank (message = "El nombre es obligatorio")
    @Size(min = 3, max = 25, message = "El Nombre debe tener entre 3 y 25 caracteres")
    @Column(nullable = false, length = 25)
    private String nombre;

    @NotBlank(message = "El Apellido tiene que ser obligatorio")
    @Size(min = 3, max = 25, message = "El Apellido debe tener entre 3 y 25 caracteres")
    @Column(nullable = false, length = 25)
    private String apellido;

    @NotBlank(message = "La edad es obligatoria")
    @Min(value = 1, message = "La edad debe ser mayor a 0")
    @Column(nullable = false)
    private Integer edad;

    @NotBlank(message = "El Rut es obligatorio")
    @Min(value = 1000000, message = "El Rut es muy corto")
    @Max(value = 99999999, message = "El Rut no puede tener más de 8 dígitos")
    @Column(nullable = false)
    private Integer rut;

    @NotBlank(message = "El dv es obligatorio")
    @Size(min = 1, max = 1, message = "El Dv tiene que tener 1 digito")
    @Column(nullable = false, length = 1)
    private String dv;

    @NotBlank(message = "El correo es obligatorio")
    @Size(min = 15, max = 40, message = "El Correo debe tener entre 15 y 40 caracteres")
    @Column(nullable = false, length = 40)
    private String correo;

    @NotNull
    @Min(value = 100000000, message = "El teléfono debe tener 9 dígitos")
    @Max(value = 999999999, message = "El teléfono debe tener 9 dígitos")
    @Column(nullable = false)
    private Integer telefono;
}