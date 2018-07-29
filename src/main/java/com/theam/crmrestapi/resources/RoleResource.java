package com.theam.crmrestapi.resources;


import com.theam.crmrestapi.exceptions.UnauthorizedException;
import com.theam.crmrestapi.model.Role;
import com.theam.crmrestapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;


/**
 * The Class RoleResource.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "role")
@Path("/role")
public class RoleResource {

    /** The role service. */
    @Autowired
    UserService userService;

    @GET
    @Produces("application/json")
    public List<Role> getAllRoles(@HeaderParam("x-access-token") String token) {
        if (token == null) {
            throw new UnauthorizedException();

        } else {
            return userService.findAllRoles();
        }
    }
}