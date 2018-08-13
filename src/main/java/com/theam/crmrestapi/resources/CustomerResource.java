package com.theam.crmrestapi.resources;

import com.theam.crmrestapi.model.Customer;
import com.theam.crmrestapi.model.User;
import com.theam.crmrestapi.services.CustomerService;
import com.theam.crmrestapi.services.UserService;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
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
     * @return the response
     * @throws URISyntaxException
     *             the URI syntax exception
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RolesAllowed({"ADMIN","USER"})
    public Response createCustomer(@FormDataParam("file") InputStream file,
                                   @FormDataParam("file") FormDataContentDisposition disposition,
                                   @FormDataParam("customer") FormDataBodyPart customerJson,
                                   @HeaderParam("authorization") String authorization) throws URISyntaxException {

        User authenticatedUser = userService.authenticatedUser(authorization);


        String uploadedFileLocation = "uploadImages/"
                + disposition.getFileName();

        writeToFile(file, uploadedFileLocation);

        customerJson.setMediaType(MediaType.APPLICATION_JSON_TYPE);
        Customer customer = customerJson.getValueAs(Customer.class);

        System.out.println(customer.getName());

        //TODO
        if (customer.getName() == null || customer.getSurname() == null) {
            return Response.status(400).entity("Please provide all mandatory inputs").build();
        }

        customer.setCreatedBy(authenticatedUser);

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        java.sql.Date date = new java.sql.Date(currentDate.getTime());
        customer.setCreationDate(date);

        customerService.createCustomer(customer);
        return Response.status(201).contentLocation(new URI("/customer/" + customer.getId())).build();

    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    @RolesAllowed({"ADMIN","USER"})
    public Response updateCustomer(@PathParam("id") int id, @HeaderParam("authorization") String authorization, Customer customerChanges) throws URISyntaxException {

        User authenticatedUser = userService.authenticatedUser(authorization);


        Customer customer = customerService.findCustomerById(id);

        if (customerChanges.getName() != null) {
            customer.setName(customerChanges.getName());
        }

        if (customerChanges.getSurname() != null) {
            customer.setSurname(customerChanges.getSurname());
        }

        customer.setUpdatedBy(authenticatedUser);
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentDate = calendar.getTime();
        java.sql.Date date = new java.sql.Date(currentDate.getTime());
        customer.setLastUpdate(date);
        customerService.updateCustomer(customer);
        return Response.status(201).contentLocation(new URI("/customer/" + customer.getId())).build();

    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"ADMIN","USER"})
    public Response deleteCustomerById( @PathParam("id") int id, @HeaderParam("authorization") String authorization) {

        Boolean eliminated = customerService.deleteCustomerById(id);
        if (!eliminated) {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found with ID: " + id).build();
        }
        return Response.status(200).build();

    }

    // save uploaded file to new location
    private void writeToFile(InputStream uploadedInputStream,
                             String uploadedFileLocation) {

        try {
            OutputStream out = new FileOutputStream(new File(
                    uploadedFileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];

            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    }
