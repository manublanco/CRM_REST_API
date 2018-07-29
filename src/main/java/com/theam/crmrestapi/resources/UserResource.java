package com.theam.crmrestapi.resources;


import com.theam.crmrestapi.exceptions.UnauthorizedException;
import com.theam.crmrestapi.model.Role;
import com.theam.crmrestapi.model.User;
import com.theam.crmrestapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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

    private static final int GUEST = 2;

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

            Set<Role> newRoles = new HashSet<Role>();

            if (user.getName() == null || user.getPassword() == null) {
                return Response.status(400).entity("Please provide all mandatory inputs").build();
            }
            if (user.getRoles() == null) {
                Role role = userService.getRoleId(GUEST);
                newRoles.add(role);
            } else {
                Set<Role> roles = user.getRoles();
                for (Role role : roles) {
                    Role newRole = userService.getRoleByName(role.getName());

                    if (newRole == null) {
                        return Response.status(400).entity("One of the roles provided is not valid").build();
                    }
                    newRoles.add(newRole);
                }
                user.setRoles(newRoles);
            }
            user.setRoles(newRoles);
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
            Set<Role> newRoles = new HashSet<Role>();

            User user = userService.findUserById(id);

            if (userChanges.getName() != null) {
                user.setName(userChanges.getName());
            }

            if (userChanges.getPassword() != null) {
                user.setPassword(userChanges.getPassword());
            }

            if (userChanges.getRoles() != null) {
                Set<Role> roles = userChanges.getRoles();

                for (Role role : roles) {

                    Role newRole = userService.getRoleByName(role.getName());

                    if (newRole == null) {
                        return Response.status(400).entity("One of the roles provided is not valid").build();
                    }

                    newRoles.add(newRole);

                }
                user.setRoles(newRoles);

            }


            userService.updateUser(user);
            return Response.status(201).contentLocation(new URI("/user/" + user.getId())).build();
        }
    }
}
