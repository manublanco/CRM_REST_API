package com.theam.crmrestapi.resources;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
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
import java.util.ArrayList;
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
        if (null == customer) {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found for ID: " + id).build();
        }
        return Response.status(Response.Status.OK).entity(customer)
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
                                   @HeaderParam("authorization") String authorization) throws URISyntaxException, IOException {

        customerJson.setMediaType(MediaType.APPLICATION_JSON_TYPE);
        Customer customer = customerJson.getValueAs(Customer.class);

        if ((null == customer.getName()) || (null == customer.getSurname())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please provide all mandatory inputs").build();
        }

        //Check authenticated user
        User authenticatedUser = userService.authenticatedUser(authorization);
        customer.setCreatedBy(authenticatedUser);

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        java.sql.Date date = new java.sql.Date(currentDate.getTime());
        customer.setCreationDate(date);

        if (file != null) {
            String fileName = customer.getName() + customer.getSurname();
            String urlPhotoField = uploadToGoogleCloud(file, fileName);
            customer.setPhotoField(urlPhotoField);
        }

        customerService.createCustomer(customer);

        return Response.status(Response.Status.CREATED).contentLocation(new URI("/customer/" + customer.getId())).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/json")
    @RolesAllowed({"ADMIN","USER"})
    public Response updateCustomer(@PathParam("id") int id,
                                   @FormDataParam("file") InputStream file,
                                   @FormDataParam("file") FormDataContentDisposition disposition,
                                   @FormDataParam("customer") FormDataBodyPart customerJson,
                                   @HeaderParam("authorization") String authorization) throws URISyntaxException, IOException {


        customerJson.setMediaType(MediaType.APPLICATION_JSON_TYPE);
        Customer customerChanges = customerJson.getValueAs(Customer.class);

        Customer customer = customerService.findCustomerById(id);
        if (null == customer) {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found for ID: " + id).build();
        }

        if (null != customerChanges.getName()) {
            customer.setName(customerChanges.getName());
        }

        if (null != customerChanges.getSurname()) {
            customer.setSurname(customerChanges.getSurname());
        }

        User authenticatedUser = userService.authenticatedUser(authorization);
        customer.setUpdatedBy(authenticatedUser);

        Calendar calendar = Calendar.getInstance();
        java.util.Date currentDate = calendar.getTime();
        java.sql.Date date = new java.sql.Date(currentDate.getTime());
        customer.setLastUpdate(date);

        if (null != file) {
            String fileName = customer.getName() + customer.getSurname();
            String urlPhotoField = uploadToGoogleCloud(file, fileName);
            customer.setPhotoField(urlPhotoField);
        }

        customerService.updateCustomer(customer);

        return Response.status(Response.Status.CREATED).contentLocation(new URI("/customer/" + customer.getId())).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"ADMIN","USER"})
    public Response deleteCustomerById( @PathParam("id") int id, @HeaderParam("authorization") String authorization) {

        Boolean eliminated = customerService.deleteCustomerById(id);
        if (!eliminated) {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found with ID: " + id).build();
        }
        return Response.status(Response.Status.OK).build();

    }

  private String uploadToGoogleCloud(InputStream file, String fileName) throws IOException {

      String SERVICE_ACCOUNT_JSON_PATH = "src/main/resources/googleCloud/crm-rest-api-213309-2cb3273f5d1f.json";

      Storage storage =
              StorageOptions.newBuilder()
                      .setCredentials(
                              ServiceAccountCredentials.fromStream(
                                      new FileInputStream(SERVICE_ACCOUNT_JSON_PATH)))
                      .build()
                      .getService();

      List<Acl> acls = new ArrayList<>();
      acls.add(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

      Blob blob =
              storage.create(
                      BlobInfo.newBuilder("crm-theam-rest-api-images", fileName + ".jpg")
                              .setContentType("image/jpeg")
                              .setAcl(acls).build(),
                      file);

      return blob.getMediaLink();
     }
    }
