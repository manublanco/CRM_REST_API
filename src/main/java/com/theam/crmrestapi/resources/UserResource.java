package com.theam.crmrestapi.resources;


import com.theam.crmrestapi.exceptions.UnauthorizedException;
import com.theam.crmrestapi.model.User;
import com.theam.crmrestapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


/**
 * The Class UserResource.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "user")
@Path("/user")
public class UserResource {

    /** The user service. */
    @Autowired
    UserService userService;

    private static final String USER = "USER";
    private static final String ADMIN = "ADMIN";

    @GET
    @Produces("application/json")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getUserById(@PathParam("id") int id) throws URISyntaxException {

        User user = userService.findUserById(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found with ID: " + id).build();
        }
        return Response.status(200).entity(user)
                .contentLocation(new URI("/user/" + id))
                .build();
    }

    /**
     * Creates the user.
     *
     * @param user
     *            the user
     * @return the response
     * @throws URISyntaxException
     *             the URI syntax exception
     */
    @POST
    @Consumes("application/json")
    public Response createUser(User user) throws URISyntaxException {

        if (user.getName() == null || user.getSurname() == null ) {
            return Response.status(400).entity("Please provide all mandatory inputs").build();
        }
        //the user is always created with the role "USER"
        //if we need to make him "ADMIN" we have to use the "makeAdmin" endpoint
        user.setRole(USER);
        userService.createUser(user);

        return Response.status(201).contentLocation(new URI("/user/" + user.getId())).build();

    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateUser(@PathParam("id") int id, User userChanges) throws URISyntaxException {

        User user = userService.findUserById(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found with ID: " + id).build();
        }

        if (userChanges.getName() != null) {
            user.setName(userChanges.getName());
        }

        if (userChanges.getSurname() != null) {
            user.setSurname(userChanges.getSurname());
        }

        if (userChanges.getRole() != null) {
            user.setRole(userChanges.getRole());
        }

        userService.updateUser(user);

        return Response.status(201).contentLocation(new URI("/user/" + user.getId())).build();

    }

    @DELETE
    @Path("/{id}")
    public Response deleteUserById(@PathParam("id") int id) {

        Boolean eliminated = userService.deleteUserById(id);

            if (!eliminated) {
                return Response.status(Response.Status.NOT_FOUND).entity("User not found with ID: " + id).build();
            }
            return Response.status(200).build();
    }

    @PUT
    @Path("/{id}/isAdmin/{isAdmin}")
    @Produces("application/json")
    public Response changeRole(@PathParam("id") int id, @PathParam("isAdmin") boolean isAdmin) throws URISyntaxException {


        User user = userService.findUserById(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found with ID: " + id).build();
        }

        if (isAdmin) {
            user.setRole(ADMIN);
        } else {
            user.setRole(USER);
        }

        userService.updateUser(user);

        return Response.status(201).contentLocation(new URI("/user/" + user.getId())).build();

    }

}
