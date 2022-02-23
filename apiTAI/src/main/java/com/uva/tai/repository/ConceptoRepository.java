package com.uva.tai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.uva.tai.model.Concepto;

public interface ConceptoRepository extends JpaRepository<Concepto, Integer> {
    
    List<Concepto> findByTaiId(Integer taiId);
    //void deleteByTaiId(Integer taiId);
}