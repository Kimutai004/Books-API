package src.main.java.com.bookstore.resources;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import src.main.java.com.bookstore.models.CartItem;

import java.util.*;

@Path("/customers/{customerId}/cart")
public class CartResource {
    private static Map<Integer, List<CartItem>> customerCarts = new HashMap<>();

    @POST
    @Path("/items")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addItemToCart(@PathParam("customerId") int customerId, CartItem item) {
        customerCarts.putIfAbsent(customerId, new ArrayList<>());
        customerCarts.get(customerId).add(item);
        return Response.status(Response.Status.CREATED).entity(customerCarts.get(customerId)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCart(@PathParam("customerId") int customerId) {
        List<CartItem> cart = customerCarts.get(customerId);
        if (cart == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Cart not found").build();
        }
        return Response.ok(cart).build();
    }

    @PUT
    @Path("/items/{bookId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCartItem(@PathParam("customerId") int customerId, @PathParam("bookId") int bookId, CartItem updatedItem) {
        List<CartItem> cart = customerCarts.get(customerId);
        if (cart == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Cart not found").build();
        }
        for (CartItem item : cart) {
            if (item.getBookId() == bookId) {
                item.setQuantity(updatedItem.getQuantity());
                return Response.ok(item).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Item not found in cart").build();
    }

    @DELETE
    @Path("/items/{bookId}")
    public Response removeCartItem(@PathParam("customerId") int customerId, @PathParam("bookId") int bookId) {
        List<CartItem> cart = customerCarts.get(customerId);
        if (cart == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Cart not found").build();
        }
        cart.removeIf(item -> item.getBookId() == bookId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}