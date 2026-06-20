package com.comics.comic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.comics.comic.model.Comic;

public interface ComicRepository extends JpaRepository<Comic, Long> {

    @Query("SELECT c FROM Comic c WHERE c.titulo = :titulo")
    Comic buscarPorNombreExacto(@Param("titulo") String titulo);
    
    List<Comic> findById(Integer id);
}
