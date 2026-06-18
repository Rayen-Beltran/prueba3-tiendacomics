package com.example.ms_cliente.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ms_cliente.model.Envio;

@Repository
public interface  EnvioRepository extends JpaRepository<Envio, Integer> {

}
