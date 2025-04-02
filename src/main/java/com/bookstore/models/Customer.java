package src.main.java.com.bookstore.models;
public class Customer {
    private int id;
    private String name;
    private String email;
    private String password;

    public Customer(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
}