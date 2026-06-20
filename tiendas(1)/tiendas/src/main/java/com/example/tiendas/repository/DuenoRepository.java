package com.example.tiendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tiendas.model.Dueno;
import com.example.tiendas.model.Empleado;

@Repository
public interface DuenoRepository extends JpaRepository<Dueno, Integer> {

    Dueno findByRut(Integer rut);
}
