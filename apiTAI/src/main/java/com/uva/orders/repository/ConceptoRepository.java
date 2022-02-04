package com.uva.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.uva.orders.model.Concepto;

public interface ConceptoRepository extends JpaRepository<Concepto, Integer> {
    
    List<Concepto> findByTaiId(Integer pedidoId);
}