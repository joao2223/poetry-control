package com.api.rifas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.rifas.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
