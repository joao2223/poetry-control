package com.api.rifas.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.rifas.dto.OrderDTO;
import com.api.rifas.entities.Order;
import com.api.rifas.servies.OrderService;

@RestController
@RequestMapping(value = "/orders")
@CrossOrigin(origins = "*")
public class OrderResource {

	@Autowired
	private OrderService service;

	@GetMapping
	public ResponseEntity<List<Order>> findAll(){
		List<Order> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Order> findById(@PathVariable Long id) {
		Order obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	 @PostMapping
	    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO orderDTO) {
	        Order newOrder = service.createOrder(orderDTO);

	        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	                .buildAndExpand(newOrder.getId()).toUri();

	        return ResponseEntity.created(uri).body(newOrder);
	    }
	 
	 @DeleteMapping("/{id}")
	 public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
		 service.deleteOrder(id);
	     return ResponseEntity.noContent().build();
	 }
}
