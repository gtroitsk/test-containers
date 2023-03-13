package org.acme;

import io.smallrye.mutiny.Uni;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }

    @GET
    @Path("/person/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Person> personGet(@PathParam("name") String name) {
        return Person.findByName(name);
    }

    @POST
    @Path("/person")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Person>> personStatus() {
        return Person.findAlive();
    }

    @DELETE
    @Path("/person/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public long personDelete(@PathParam("name") String name) {
        return Person.deleteUser(name);
    }

}