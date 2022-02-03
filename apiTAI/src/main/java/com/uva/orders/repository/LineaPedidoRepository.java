package com.uva.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.uva.orders.model.LineaPedido;

public interface LineaPedidoRepository extends JpaRepository<LineaPedido, Integer> {
    
    List<LineaPedido> findByPedidoId(Integer pedidoId);
}