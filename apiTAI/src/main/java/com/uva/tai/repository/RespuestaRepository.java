package com.uva.tai.repository;

import java.util.List;

import com.uva.tai.model.Respuesta;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RespuestaRepository extends JpaRepository<Respuesta, Integer> {
    //boolean existsByCode(String code);
    Respuesta findTopByOrderByIdDesc();
    
    List<Respuesta> findByIdTai(Integer idTai);
}