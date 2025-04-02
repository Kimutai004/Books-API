package src.main.java.com.bookstore.resources;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import src.main.java.com.bookstore.models.Customer;

import java.util.*;

@Path("/customers")
public class CustomerResource {
    private static Map<Integer, Customer> customers = new HashMap<>();
    private static int currentId = 1;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(Customer customer) {
        customer.setId(currentId++);
        customers.put(customer.getId(), customer);
        return Response.status(Response.Status.CREATED).entity(customer).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCustomers() {
        return Response.ok(new ArrayList<>(customers.values())).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerById(@PathParam("id") int id) {
        Customer customer = customers.get(id);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
        }
        return Response.ok(customer).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(@PathParam("id") int id, Customer updatedCustomer) {
        Customer customer = customers.get(id);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
        }
        updatedCustomer.setId(id);
        customers.put(id, updatedCustomer);
        return Response.ok(updatedCustomer).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        if (!customers.containsKey(id)) {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
        }
        customers.remove(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}