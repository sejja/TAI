package com.uva.tai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.uva.tai.model.Elemento;




public interface ElementoRepository extends JpaRepository<Elemento, Integer> {
    
    List<Elemento> findByRespId(Integer respId);
}