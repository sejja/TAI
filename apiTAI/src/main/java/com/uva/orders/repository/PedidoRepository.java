package com.uva.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

//import java.util.List;

import com.uva.orders.model.Tai;

public interface PedidoRepository extends JpaRepository<Tai, Integer> {

}