package org.fiap.com.br;

import org.fiap.com.br.entities.PokemonCard;
import org.fiap.com.br.entities.PokemonCollection;
import org.fiap.com.br.repositories.PokemonCardRepository;
import org.fiap.com.br.repositories.PokemonCollectionRepository;
import org.fiap.com.br.repositories.StarterDb;

import java.io.IOException;

public class Teste {
    public static void main(String[] args) throws IOException {
        new StarterDb().initialize();

        // Criação manual das instâncias para demonstração
        PokemonCollectionRepository collectionRepository = new PokemonCollectionRepository();
        PokemonCardRepository cardRepository = new PokemonCardRepository();


        //Coleções
        PokemonCollection collection1 = new PokemonCollection();
        collection1.setName("Pokemons de Iniciais");
        collection1.setDescription("Uma coleção com os pokemon iniciais da primeira geração");

        PokemonCollection collection2 = new PokemonCollection();
        collection2.setName("Passáros Lendários");
        collection2.setDescription("Uma coleção com os pokemons lendários da primeira geração");

        collectionRepository.create(collection1);
        collectionRepository.create(collection2);


        //Cartas para a primeira coleção
        PokemonCard card1 = new PokemonCard("Bulbasaur", "Pokémon Básico", "Planta", 70, "For some time after its birth, it uses the nutrients that are packed into the seed on its back in order to grow.", 1);
        cardRepository.create(card1);
        PokemonCard card2 = new PokemonCard("Squirtle", "Pokémon Básico", "Água", 70, "After birth, its back swells and hardens into a shell. It sprays a potent foam from its mouth.", 1);
        cardRepository.create(card2);
        PokemonCard card3 = new PokemonCard("Charmander", "Pokémon Básico", "Fogo", 70, "The flame on its tail shows the strength of its life-force. If Charmander is weak, the flame also burns weakly.", 1);
        cardRepository.create(card3);

        //Cartas para a segunda coleção
        PokemonCard card4 = new PokemonCard("Articuno", "Pokémon Básico", "Água", 110, "This legendary bird Pokémon can create blizzards by freezing moisture in the air.", 2);
        cardRepository.create(card4);
        PokemonCard card5 = new PokemonCard("Zapdos", "Pokémon Básico", "Raios", 110, "This legendary Pokémon is said to live in thunderclouds. It freely controls lightning bolts.", 2);
        cardRepository.create(card5);
        PokemonCard card6 = new PokemonCard("Moltres", "Pokémon Básico", "Fogo", 120, "It is one of the legendary bird Pokémon. Its appearance is said to indicate the coming of spring.", 2);
        cardRepository.create(card6);

        System.out.println(collectionRepository.getAll().toString());
        System.out.println(cardRepository.getAll().toString());

        PokemonCard cardTeste = new PokemonCard("Moltres", "Pokémon Básico", "Fogo", 120, "It is one of the legendary bird Pokémon. Its appearance is said to indicate the coming of spring.", 3);

        cardRepository.update(1, cardTeste);

        System.out.println(collectionRepository.getCardsByCollectionId(1).toString());
    }
}
