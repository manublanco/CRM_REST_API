package com.theam.crmrestapi.resources;

import com.theam.crmrestapi.exceptions.UnauthorizedException;
import com.theam.crmrestapi.model.Customer;
import com.theam.crmrestapi.model.User;
import com.theam.crmrestapi.services.CustomerService;
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
import java.util.Set;

/**
 * The Class CustomerResource.
 */

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "customer")
@Path("/customer")
public class CustomerResource {
    /** The customer service. */
    @Autowired
    CustomerService customerService;

    /** The user service. */
    @Autowired
    UserService userService;

    @GET
    @Produces("application/json")
    public List<Customer> getAllCustomers(@HeaderParam("x-access-token") String token) {
        if (token == null) {
            throw new UnauthorizedException();
        } else {
            return customerService.findAllCustomers();
        }
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getCustomerById(@PathParam("id") int id, @HeaderParam("x-access-token") String token) throws URISyntaxException {

        if (token == null) {
            throw new UnauthorizedException();
        } else {
            Customer customer = customerService.findCustomerById(id);
            if (customer == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + id).build();
            }
            return Response.status(200).entity(customer)
                    .contentLocation(new URI("/customer/" + id))
                    .build();
        }
    }

    /**
     * Creates the customer.
     *
     * @param customer
     *            the customer
     * @return the response
     * @throws URISyntaxException
     *             the URI syntax exception
     */
    @POST
    @Consumes("application/json")
    public Response createCustomer(Customer customer, @HeaderParam("x-access-token") String token) throws URISyntaxException {

        if (token == null) {
            throw new UnauthorizedException();
        } else {
            //TODO
            if (customer.getName() == null || customer.getSurname() == null) {
                return Response.status(400).entity("Please provide all mandatory inputs").build();
            }

            customerService.createCustomer(customer);
            return Response.status(201).contentLocation(new URI("/customer/" + customer.getId())).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateCustomer(@PathParam("id") int id, Customer customerChanges, @HeaderParam("x-access-token") String token) throws URISyntaxException {
        if (token == null) {
            throw new UnauthorizedException();
        } else {

            Customer customer = customerService.findCustomerById(id);

            if (customerChanges.getName() != null) {
                customer.setName(customerChanges.getName());
            }

            if (customerChanges.getSurname() != null) {
                customer.setSurname(customerChanges.getSurname());
            }

            customerService.updateCustomer(customer);
            return Response.status(201).contentLocation(new URI("/customer/" + customer.getId())).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomerById(@HeaderParam("x-access-token") String token, @PathParam("id") int id) {
        if (token == null) {
            throw new UnauthorizedException();
        } else {
            Boolean eliminated = customerService.deleteCustomerById(id);
            if (!eliminated) {
                return Response.status(Response.Status.NOT_FOUND).entity("Customer not found with ID: " + id).build();
            }
            return Response.status(200).build();
        }
    }
}
