package com.theam.crmrestapi.resources;

import com.theam.crmrestapi.model.Customer;
import com.theam.crmrestapi.services.CustomerService;
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
    @RolesAllowed({"ADMIN","USER"})
    public List<Customer> getAllCustomers() {
        return customerService.findAllCustomers();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    @RolesAllowed({"ADMIN","USER"})
    public Response getCustomerById(@PathParam("id") int id) throws URISyntaxException {

        Customer customer = customerService.findCustomerById(id);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + id).build();
        }
        return Response.status(200).entity(customer)
                .contentLocation(new URI("/customer/" + id))
                .build();

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
    @RolesAllowed({"ADMIN","USER"})
    public Response createCustomer(Customer customer) throws URISyntaxException {

        //TODO
        if (customer.getName() == null || customer.getSurname() == null) {
            return Response.status(400).entity("Please provide all mandatory inputs").build();
        }

        customerService.createCustomer(customer);
        return Response.status(201).contentLocation(new URI("/customer/" + customer.getId())).build();

    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    @RolesAllowed({"ADMIN","USER"})
    public Response updateCustomer(@PathParam("id") int id, Customer customerChanges) throws URISyntaxException {

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

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"ADMIN","USER"})
    public Response deleteCustomerById( @PathParam("id") int id) {

        Boolean eliminated = customerService.deleteCustomerById(id);
        if (!eliminated) {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found with ID: " + id).build();
        }
        return Response.status(200).build();

    }
}
