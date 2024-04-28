package org.fiap.com.br.services;

import org.fiap.com.br.entities.PokemonCard;
import org.fiap.com.br.entities.PokemonCollection;
import org.fiap.com.br.repositories.PokemonCollectionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PokemonCollectionServices {
    private final PokemonCollectionRepository collectionRepository;

    public PokemonCollectionServices(PokemonCollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    public void create(PokemonCollection collection) {
        Map<Boolean, ArrayList<String>> validationResult = collection.validate();
        if (!validationResult.get(true).isEmpty()) {
            // Tratar erros de validação aqui
            System.out.println("Erros de validação: " + validationResult.get(false));
            return;
        }
        collectionRepository.create(collection);
    }

    public PokemonCollection get(int id) {
        return collectionRepository.get(id);
    }

    public List<PokemonCollection> getAll() {
        return collectionRepository.getAll();
    }

    public boolean update(int id, PokemonCollection collection) {
        Map<Boolean, ArrayList<String>> validationResult = collection.validate();
        if (!validationResult.get(true).isEmpty()) {
            // Tratar erros de validação aqui
            System.out.println("Erros de validação: " + validationResult.get(false));
            return false;
        }
        return collectionRepository.update(id, collection);
    }

    public boolean delete(int id) {
        return collectionRepository.delete(id);
    }

    public List<PokemonCard> getCardsByCollectionId(int collectionId){
        return collectionRepository.getCardsByCollectionId(collectionId);
    }
}