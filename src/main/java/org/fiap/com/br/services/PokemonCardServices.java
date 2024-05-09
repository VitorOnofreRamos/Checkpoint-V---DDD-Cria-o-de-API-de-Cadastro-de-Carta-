package org.fiap.com.br.services;

import org.fiap.com.br.entities.PokemonCard;
import org.fiap.com.br.repositories.PokemonCardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PokemonCardServices {
    private final PokemonCardRepository cardRepository;

    public PokemonCardServices(PokemonCardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void create(PokemonCard card) {
        /*Map<Boolean, ArrayList<String>> validationResult = card.validate();
        if (!validationResult.get(true).isEmpty()) {
            // Tratar erros de validação aqui
            System.out.println("Erros de validação: " + validationResult.get(false));
            return;
        }*/
        cardRepository.create(card);
    }

    public PokemonCard get(int id) {
        return cardRepository.get(id);
    }

    public List<PokemonCard> getAll() {
        return cardRepository.getAll();
    }

    public boolean update(int id, PokemonCard card) {
        /*Map<Boolean, ArrayList<String>> validationResult = card.validate();
        if (!validationResult.get(true).isEmpty()) {
            // Tratar erros de validação aqui
            System.out.println("Erros de validação: " + validationResult.get(false));
            return false;
        }*/
        return cardRepository.update(id, card);
    }

    public boolean delete(int id) {
        return cardRepository.delete(id);
    }

}
