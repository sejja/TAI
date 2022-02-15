package com.uva.tai.repository;

import com.uva.tai.model.Respuesta;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RespuestaRepository extends JpaRepository<Respuesta, Integer> {
    //boolean existsByCode(String code);
}