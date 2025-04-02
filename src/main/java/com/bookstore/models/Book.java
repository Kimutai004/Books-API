package src.main.java.com.bookstore.models;
public class Book {
    private int id;
    private String title;
    private int authorId;
    private String isbn;
    private int publicationYear;
    private double price;
    private int stock;

    public Book(int id, String title, int authorId, String isbn, int publicationYear, double price, int stock) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.price = price;
        this.stock = stock;
    }

    // Getters and Setters
}