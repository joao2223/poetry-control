package com.api.rifas.servies;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.rifas.dto.OrderDTO;
import com.api.rifas.entities.Order;
import com.api.rifas.entities.OrderItem;
import com.api.rifas.entities.User;
import com.api.rifas.repositories.OrderItemRepository;
import com.api.rifas.repositories.OrderRepository;
import com.api.rifas.repositories.UserRepository;
import com.api.rifas.servies.exceptions.ResourceNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private RaffleNumberService raffleNumberService;

	@Transactional
	public List<Order> findAll() {
		return repository.findAll();
	}

	@Transactional
	public Order findById(Long id) {
		Optional<Order> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Transactional
    public Order createOrder(OrderDTO orderDTO) {
        User client = userRepository.findById(orderDTO.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException(orderDTO.getClientId()));

        Order newOrder = new Order();
        newOrder.setClient(client);
        newOrder.setMoment(Instant.now()); // Adicione o Instant de emissão

        return repository.save(newOrder);
    }
	
	public void deleteOrder(Long orderId) {
	    // Primeiro, encontre o pedido pelo ID
	    Order order = repository.findById(orderId)
	            .orElseThrow(() -> new ResourceNotFoundException(orderId));

	    // Obtenha o conjunto de números gerados desse pedido
	    Set<Integer> generatedNumbers = order.getItems().stream()
	            .flatMap(item -> item.getGeneratedNumbers().stream())
	            .collect(Collectors.toSet());

	    // Remova os números gerados do serviço RaffleNumberService
	    raffleNumberService.removeGeneratedNumbers(orderId, generatedNumbers);

	    // Em seguida, exclua todos os itens de pedido associados a este pedido
	    for (OrderItem item : order.getItems()) {
	        orderItemRepository.delete(item);
	    }

	    // Finalmente, exclua o próprio pedido
	    repository.delete(order);
	}
}
