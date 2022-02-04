package com.uva.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.uva.orders.model.Palabra;

public interface LineaPedidoRepository extends JpaRepository<Palabra, Integer> {
    
    List<Palabra> findByPedidoId(Integer pedidoId);
}