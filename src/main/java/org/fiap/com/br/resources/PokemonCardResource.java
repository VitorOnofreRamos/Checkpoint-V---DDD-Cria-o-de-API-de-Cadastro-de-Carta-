package org.fiap.com.br.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.fiap.com.br.entities.PokemonCard;
import org.fiap.com.br.repositories.PokemonCardRepository;
import org.fiap.com.br.services.PokemonCardServices;
import org.fiap.com.br.utils.Log4jLogger;

import java.util.List;

@Path("cards")
public class PokemonCardResource {
    private final Log4jLogger<PokemonCard> logger = new Log4jLogger<>(PokemonCard.class);
    private final PokemonCardServices cardServices;
    private final PokemonCardRepository cardRepository;

    public PokemonCardResource() {
        this.cardRepository = new PokemonCardRepository();
        this.cardServices = new PokemonCardServices(cardRepository);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PokemonCard> getAll(){
        logger.logGetAll(null);
        return cardServices.getAll();
    }

    @POST
    @Path("card")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(PokemonCard card){
        logger.logCreate(card);
        cardServices.create(card);
        return Response.status(Response.Status.CREATED).entity(card).build();
    }

    @PUT
    @Path("card/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, PokemonCard card){
        if (cardServices.get(id) != null){
            logger.logUpdate(card);
            cardServices.update(id, card);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(card).build();
        }
    }

    @DELETE
    @Path("card/{id}")
    public Response update(@PathParam("id") int id){
        if (cardServices.get(id) != null){
            logger.logDelete(null);
            cardServices.delete(id);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("card/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id){
        PokemonCard card = cardServices.get(id);
        if (card != null){
            logger.logGet(null);
            return Response.status(Response.Status.OK).entity(card).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
