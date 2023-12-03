package com.api.rifas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.rifas.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
	
	@Query("SELECT oi FROM OrderItem oi WHERE oi.id.raffle.id = :raffleId")
    List<OrderItem> findAllByRaffleId(Long raffleId);
}
