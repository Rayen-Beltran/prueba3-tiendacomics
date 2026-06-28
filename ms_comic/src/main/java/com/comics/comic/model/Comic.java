package com.comics.comic.model;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
@Table(name = "comics")
public class Comic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El titulo es obligatorio")
    @Size(min = 3, max = 100, message = "El titulo debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String titulo;

    @NotBlank(message = "El ISBN es obligatorio")
    @Size(min = 10, max = 13, message = "El ISBN debe tener entre 10 y 13 caracteres")
    @Column(nullable = false, length = 13)
    private String ISBN;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    @Column(nullable = false)
    private Double precio;

    @NotNull(message = "la fecha de publicacion es obligatoria")
    @Column(nullable = false)
    private LocalDate fechaPublicacion;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(nullable = false)
    private Integer stock;

    @ManyToMany
    @JoinTable(name = "comic_editorial", joinColumns = @JoinColumn(name = "id_comic"), inverseJoinColumns = @JoinColumn(name = "id_editorial"))
    private List<Editorial> editoriales;

    @ManyToMany
    @JoinTable(name = "comic_categoria", joinColumns = @JoinColumn(name = "id_comic"), inverseJoinColumns = @JoinColumn(name = "id_categoria"))
    private List<Categoria> categorias;

    @ManyToMany
    @JoinTable(name = "autor_comic", joinColumns = @JoinColumn(name = "id_comic"), inverseJoinColumns = @JoinColumn(name = "id_autor"))
    private List<Autor> autores;

    @ManyToMany
    @JoinTable(name = "comic_tienda", joinColumns = @JoinColumn(name = "comic_id"), inverseJoinColumns = @JoinColumn(name = "tienda_id"))
    private List<Tienda> tiendas;

}
