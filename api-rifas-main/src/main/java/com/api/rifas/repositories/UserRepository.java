package com.api.rifas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.rifas.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
