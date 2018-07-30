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

    @GET
    @Produces("application/json")
    public List<User> getAllUsers(@HeaderParam("x-access-token") String token) {
        if (token == null) {
            throw new UnauthorizedException();
        } else {
            return userService.findAllUsers();
        }
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getUserById(@PathParam("id") int id, @HeaderParam("x-access-token") String token) throws URISyntaxException {

        if (token == null) {
            throw new UnauthorizedException();
        } else {
            User user = userService.findUserById(id);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + id).build();
            }
            return Response.status(200).entity(user)
                    .contentLocation(new URI("/user/" + id))
                    .build();
        }
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
    public Response createUser(User user, @HeaderParam("x-access-token") String token) throws URISyntaxException {

        if (token == null) {
            throw new UnauthorizedException();
        } else {

            if (user.getName() == null || user.getPassword() == null) {
                return Response.status(400).entity("Please provide all mandatory inputs").build();
            }
            if (user.getRole() == null) {
                user.setRole(USER);
            } else {
                user.setRole(user.getRole());
            }
            userService.createUser(user);
            return Response.status(201).contentLocation(new URI("/user/" + user.getId())).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateUser(@PathParam("id") int id, User userChanges, @HeaderParam("x-access-token") String token) throws URISyntaxException {
        if (token == null) {
            throw new UnauthorizedException();
        } else {
            User user = userService.findUserById(id);

            if (userChanges.getName() != null) {
                user.setName(userChanges.getName());
            }

            if (userChanges.getPassword() != null) {
                user.setPassword(userChanges.getPassword());
            }

            if (userChanges.getRole() != null) {
                user.setRole(userChanges.getRole());
            }


            userService.updateUser(user);
            return Response.status(201).contentLocation(new URI("/user/" + user.getId())).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUserById(@HeaderParam("x-access-token") String token, @PathParam("id") int id) {
        if (token == null) {
            throw new UnauthorizedException();
        } else {
            Boolean eliminated = userService.deleteUserById(id);
            if (!eliminated) {
                return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + id).build();
            }
            return Response.status(200).build();
        }
    }
}
