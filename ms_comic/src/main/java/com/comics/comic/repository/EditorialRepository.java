package com.comics.comic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.comics.comic.model.Editorial;

@Repository
public interface EditorialRepository extends JpaRepository<Editorial, Integer> {
}