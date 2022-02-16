package com.uva.tai.repository;

import java.util.List;
import java.util.Optional;

import com.uva.tai.model.Resultado;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultadoRepository extends JpaRepository<Resultado, Integer> {
    //boolean existsByCode(String code);
    
    List<Resultado> findByIdTai(Integer idTai);
    
    Optional<Resultado> findByIdResp(Integer idResp);

}