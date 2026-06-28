package com.comics.comic.model;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre del Autor es obligatorio")
    @Size(min = 3, max = 15, message = "El nombre del autor tiene que tener entre 3 y 15 caracteres")
    @Column(nullable = false, length = 15)
    private String nombre;

    @ManyToMany(mappedBy = "autores")
    @ToString.Exclude
    private List<Comic> comics;

    public Integer getId_autor() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}