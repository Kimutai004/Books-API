package src.main.java.com.bookstore.models;
import java.util.List;

public class Order {
    private int id;
    private int customerId;
    private List<CartItem> items;

    public Order(int id, int customerId, List<CartItem> items) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
    }

    // Getters and Setters
}