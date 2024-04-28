package org.fiap.com.br;

import org.fiap.com.br.entities.PokemonCard;
import org.fiap.com.br.entities.PokemonCollection;
import org.fiap.com.br.repositories.PokemonCardRepository;
import org.fiap.com.br.repositories.PokemonCollectionRepository;
import org.fiap.com.br.repositories.StarterDb;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in org.fiap.com.br package
        final ResourceConfig rc = new ResourceConfig().packages("org.fiap.com.br");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
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

        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with endpoints available at "
                + "%s%nHit Ctrl-C to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}

