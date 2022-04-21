package com.uva.tai.repository;

import com.uva.tai.model.Tai;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaiRepository extends JpaRepository<Tai, Integer> {
    boolean existsByCode(String code);
    Tai findTopByOrderByIdDesc();
}