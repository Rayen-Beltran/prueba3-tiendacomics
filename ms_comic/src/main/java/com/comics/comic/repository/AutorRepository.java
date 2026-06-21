package com.comics.comic.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.comics.comic.model.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Integer> {
    List<Autor> findByNombre(String nombre);

}
