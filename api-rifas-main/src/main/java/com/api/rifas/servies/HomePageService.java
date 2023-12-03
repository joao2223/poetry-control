package com.api.rifas.servies;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.rifas.entities.HomePage;
import com.api.rifas.repositories.HomePageRepository;
import com.api.rifas.servies.exceptions.DatabaseException;
import com.api.rifas.servies.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class HomePageService {
	
	@Autowired
	private HomePageRepository repository;
	
	@Transactional
	public List<HomePage> findAll() {
		return repository.findAll();
	}

	@Transactional
	public HomePage findById(Long id) {
		Optional<HomePage> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Transactional
	public HomePage insert(HomePage obj) {
	    return repository.save(obj);
	}

	public void delete(Long id) {
		try {
			if (repository.existsById(id)) {
				repository.deleteById(id);
			} else {
				throw new ResourceNotFoundException(id);
			}
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Transactional
	public HomePage update(Long id, HomePage obj) {
		try {
			HomePage entity = repository.getReferenceById(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	public void updateData(HomePage entity, HomePage obj) {
		entity.setImgHomePage(obj.getImgHomePage());
		entity.setImgLogo(obj.getImgLogo());
	}
}
