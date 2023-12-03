package com.api.rifas.servies;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.rifas.entities.OrderItem;
import com.api.rifas.entities.User;
import com.api.rifas.repositories.OrderItemRepository;
import com.api.rifas.servies.exceptions.DatabaseException;
import com.api.rifas.servies.exceptions.ExceededRaffleLimitException;

@Service
public class OrderItemService {

	@Autowired
	private OrderItemRepository repository;

	@Autowired
	private RaffleNumberService raffleNumberService;

	@Transactional
	public List<OrderItem> findAll() {
		return repository.findAll();
	}

	@Transactional
	public OrderItem findById(Long id) {
		Optional<OrderItem> obj = repository.findById(id);
		return obj.get();
	}

	@Transactional
	public OrderItem insert(OrderItem obj) {
		return repository.save(obj);
	}

	@Transactional
	public OrderItem updateQuantity(Long itemId, Integer newQuantity) {
		OrderItem item = repository.findById(itemId).orElseThrow(() -> new RuntimeException("OrderItem not found"));
		item.setQuantity(newQuantity);
		return repository.save(item);
	}

	@Transactional
	public OrderItem save(OrderItem orderItem) {
		return repository.save(orderItem);
	}

	@Transactional
	public OrderItem createOrderItem(OrderItem orderItem) {
	    Long raffleId = orderItem.getRaffle().getId();
	    int maxNumber = orderItem.getRaffle().getQuantity();
	    int quantity = orderItem.getQuantity();
	    User user = orderItem.getOrder().getClient(); // Substitua por como você obtém o usuário associado

	    try {
	        Set<Integer> generatedNumbers = raffleNumberService.generateNumbers(raffleId, user, quantity, maxNumber);
	        orderItem.setGeneratedNumbers(generatedNumbers);
	        return repository.save(orderItem);
	    } catch (ExceededRaffleLimitException e) {
	        throw new DatabaseException(e.getMessage()); // Lançar uma exceção específica para erro de limite excedido
	    }
	}
}
