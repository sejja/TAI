package com.uva.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

//import java.util.List;

import com.uva.orders.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}