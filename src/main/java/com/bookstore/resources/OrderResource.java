package src.main.java.com.bookstore.resources;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import src.main.java.com.bookstore.models.CartItem;
import src.main.java.com.bookstore.models.Order;

import java.util.*;

@Path("/customers/{customerId}/orders")
public class OrderResource {
    private static Map<Integer, List<Order>> customerOrders = new HashMap<>();
    private static int currentOrderId = 1;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(@PathParam("customerId") int customerId) {
        List<CartItem> cart = CartResource.customerCarts.get(customerId);
        if (cart == null || cart.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Cart is empty or not found").build();
        }
        Order order = new Order(currentOrderId++, customerId, new ArrayList<>(cart));
        customerOrders.putIfAbsent(customerId, new ArrayList<>());
        customerOrders.get(customerId).add(order);
        CartResource.customerCarts.remove(customerId); // Clear cart after order
        return Response.status(Response.Status.CREATED).entity(order).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOrders(@PathParam("customerId") int customerId) {
        List<Order> orders = customerOrders.get(customerId);
        if (orders == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No orders found").build();
        }
        return Response.ok(orders).build();
    }

    @GET
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderById(@PathParam("customerId") int customerId, @PathParam("orderId") int orderId) {
        List<Order> orders = customerOrders.get(customerId);
        if (orders == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No orders found").build();
        }
        for (Order order : orders) {
            if (order.getId() == orderId) {
                return Response.ok(order).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Order not found").build();
    }
}
``