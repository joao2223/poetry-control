package com.api.rifas.servies;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.api.rifas.entities.User;
import com.api.rifas.servies.exceptions.ExceededRaffleLimitException;

@Service
public class RaffleNumberService {

    private Map<Long, Set<Integer>> raffleNumbersMap = new HashMap<>();

    public Set<Integer> generateNumbers(Long raffleId, User user, int quantity, int maxNumber) {
        Set<Integer> existingNumbers = raffleNumbersMap.getOrDefault(raffleId, new HashSet<>());

        if (existingNumbers.size() + quantity > maxNumber) {
            throw new ExceededRaffleLimitException("Exceeded raffle limit for user: " + user.getId());
        }

        Set<Integer> generatedNumbers = new HashSet<>();
        while (generatedNumbers.size() < quantity) {
            int randomNumber = ThreadLocalRandom.current().nextInt(1, maxNumber + 1);

            if (!existingNumbers.contains(randomNumber)) {
                generatedNumbers.add(randomNumber);
                existingNumbers.add(randomNumber);
            }
        }

        raffleNumbersMap.put(raffleId, existingNumbers); // Atualiza os números gerados para a rifa
        return generatedNumbers;
    }

    public void removeGeneratedNumbers(Long raffleId, Set<Integer> generatedNumbers) {
        Set<Integer> existingNumbers = raffleNumbersMap.getOrDefault(raffleId, new HashSet<>());
        existingNumbers.removeAll(generatedNumbers);
        raffleNumbersMap.put(raffleId, existingNumbers);
    }
}
