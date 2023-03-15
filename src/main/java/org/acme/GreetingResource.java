package org.acme;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/person")
public class GreetingResource {

    @GET
    @Path("/hello/world")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }

    @GET
    @Path("/{name}")
    public Person personGet(@PathParam("name") String name) {
        return Person.findByName(name);
    }

    @GET
    public List<Person> personStatus() {
        return Person.findAlive();
    }

    @POST
    @Path("/person")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createPerson(Person person) {
        person.persist();
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/{name}")
    public long personDelete(@PathParam("name") String name) {
        return Person.deleteUser(name);
    }

}