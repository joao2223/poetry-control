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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.rifas.entities.Raffle;
import com.api.rifas.servies.RaffleService;

@RestController
@RequestMapping(value = "/raffles")
@CrossOrigin(origins = "*")
public class RaffleResource {

	@Autowired
	private RaffleService service;

	@GetMapping
	public ResponseEntity<List<Raffle>> findAll(){
		List<Raffle> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Raffle> findById(@PathVariable Long id) {
		Raffle obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping(value = "/{id}/remaining")
	public ResponseEntity<Long> getRemainingRaffles(@PathVariable Long id) {
	    Long remainingRaffles = service.calculateRemainingRaffles(id);
	    return ResponseEntity.ok(remainingRaffles);
	}
	
	@PostMapping
	public ResponseEntity<Raffle> insert(@RequestBody Raffle obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Raffle> update(@PathVariable Long id, @RequestBody Raffle obj) {
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
}
