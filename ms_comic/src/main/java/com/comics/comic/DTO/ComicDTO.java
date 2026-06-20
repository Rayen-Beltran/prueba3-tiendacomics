package com.comics.comic.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ComicDTO {
    public Integer id;
    public String titulo;
    public String ISBN;
    public String editorial;
    public String autor;
    public String genero;
    public Double precio;
    public Integer stock;
    public LocalDate fechaPublicacion;

    public void setId_comic(Integer id2) {
        this.id = id2;
    }

    public Integer getId_comic() {
        return id;
    }
}
