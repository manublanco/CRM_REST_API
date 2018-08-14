package com.theam.crmrestapi.resources;

import com.theam.crmrestapi.model.User;
import com.theam.crmrestapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.security.RolesAllowed;
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
    @RolesAllowed("ADMIN")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    @RolesAllowed("ADMIN")
    public Response getUserById(@PathParam("id") int id) throws URISyntaxException {

        User user = userService.findUserById(id);
        if (null == user) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found with ID: " + id).build();
        }
        return Response.status(Response.Status.OK).entity(user)
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
    @RolesAllowed("ADMIN")
    public Response createUser(User user) throws URISyntaxException {

        if ((null == user.getName()) || (null == user.getSurname())
            || (null == user.getUsername()) || (null == user.getPassword())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please provide all mandatory inputs").build();
        }

        User checkUser = userService.findUserByUsername(user.getUsername());

        if (null != checkUser) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Username already taken").build();
        }

        //the user is always created with the role "USER"
        //if we need to make him "ADMIN" we have to use the "changeRole" endpoint
        user.setRole(USER);
        userService.createUser(user);

        return Response.status(Response.Status.CREATED).contentLocation(new URI("/user/" + user.getId())).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    @RolesAllowed("ADMIN")
    public Response updateUser(@PathParam("id") int id, User userChanges) throws URISyntaxException {

        User user = userService.findUserById(id);

        if (null == user) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found with ID: " + id).build();
        }

        if (null != userChanges.getName()) {
            user.setName(userChanges.getName());
        }

        if (null != userChanges.getSurname()) {
            user.setSurname(userChanges.getSurname());
        }

        if (null != userChanges.getUsername()) {
            user.setUsername(userChanges.getUsername());
        }

        if (null != userChanges.getPassword()) {
            user.setPassword(userChanges.getPassword());
        }


        userService.updateUser(user);

        return Response.status(Response.Status.OK).contentLocation(new URI("/user/" + user.getId())).build();

    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
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
    @RolesAllowed("ADMIN")
    public Response changeRole(@PathParam("id") int id, @PathParam("isAdmin") boolean isAdmin) throws URISyntaxException {

        User user = userService.findUserById(id);

        if (null == user) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found with ID: " + id).build();
        }

        if (isAdmin) {
            user.setRole(ADMIN);
        } else {
            user.setRole(USER);
        }

        userService.updateUser(user);

        return Response.status(Response.Status.OK).contentLocation(new URI("/user/" + user.getId())).build();
    }
}
