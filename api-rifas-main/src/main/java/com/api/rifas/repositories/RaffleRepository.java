package com.api.rifas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.rifas.entities.Raffle;

public interface RaffleRepository  extends JpaRepository<Raffle, Long>{

}
