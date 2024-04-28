package org.fiap.com.br.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.fiap.com.br.entities.PokemonCard;
import org.fiap.com.br.entities.PokemonCollection;
import org.fiap.com.br.services.PokemonCollectionServices;
import org.fiap.com.br.utils.Log4jLogger;

import java.util.List;

@Path("collections")
public class PokemonCollectionResource {
    private final Log4jLogger<PokemonCollection> logger = new Log4jLogger<>(PokemonCollection.class);
    private final PokemonCollectionServices collectionServices;

    public PokemonCollectionResource(PokemonCollectionServices collectionServices) {
        this.collectionServices = collectionServices;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PokemonCollection> getAll(){
        logger.logGetAll(null);
        return collectionServices.getAll();
    }

    @POST
    @Path("collection")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(PokemonCollection collection){
        logger.logCreate(collection);
        collectionServices.create(collection);
        return Response.status(Response.Status.CREATED).entity(collection).build();
    }

    @PUT
    @Path("collection/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, PokemonCollection collection){
        if (collectionServices.get(id) != null){
            logger.logUpdate(collection);
            collectionServices.update(id, collection);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(collection).build();
        }
    }

    @DELETE
    @Path("collection/{id}")
    public Response delete(@PathParam("id") int id){
        if (collectionServices.get(id) != null){
            logger.logDelete(null);
            collectionServices.delete(id);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("collection/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id){
        PokemonCollection collection = collectionServices.get(id);
        if (collection != null){
            logger.logGet(null);
            return Response.status(Response.Status.OK).entity(collection).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("{id}/cards")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCardsByCollectionId(@PathParam("id") int id){
        List<PokemonCard> cards = collectionServices.getCardsByCollectionId(id);
        if (cards != null && !cards.isEmpty()){
            logger.logGet(null);
            return Response.status(Response.Status.OK).entity(cards).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}

