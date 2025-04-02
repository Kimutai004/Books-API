package src.main.java.com.bookstore.resources;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import src.main.java.com.bookstore.models.Book;

import java.util.*;

@Path("/books")
public class BookResource {
    private static Map<Integer, Book> books = new HashMap<>();
    private static int currentId = 1;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBook(Book book) {
        book.setId(currentId++);
        books.put(book.getId(), book);
        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks() {
        return Response.ok(new ArrayList<>(books.values())).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookById(@PathParam("id") int id) {
        Book book = books.get(id);
        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Book not found").build();
        }
        return Response.ok(book).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("id") int id, Book updatedBook) {
        Book book = books.get(id);
        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Book not found").build();
        }
        updatedBook.setId(id);
        books.put(id, updatedBook);
        return Response.ok(updatedBook).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") int id) {
        if (!books.containsKey(id)) {
            return Response.status(Response.Status.NOT_FOUND).entity("Book not found").build();
        }
        books.remove(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}