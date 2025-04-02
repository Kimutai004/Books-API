package src.main.java.com.bookstore.resources;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import src.main.java.com.bookstore.models.Author;
import src.main.java.com.bookstore.models.Book;

import java.util.*;

@Path("/authors")
public class AuthorResource {
    private static Map<Integer, Author> authors = new HashMap<>();
    private static Map<Integer, Book> books = BookResource.getBooks(); // Assuming BookResource provides access to books
    private static int currentId = 1;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAuthor(Author author) {
        author.setId(currentId++);
        authors.put(author.getId(), author);
        return Response.status(Response.Status.CREATED).entity(author).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAuthors() {
        return Response.ok(new ArrayList<>(authors.values())).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuthorById(@PathParam("id") int id) {
        Author author = authors.get(id);
        if (author == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Author not found").build();
        }
        return Response.ok(author).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAuthor(@PathParam("id") int id, Author updatedAuthor) {
        Author author = authors.get(id);
        if (author == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Author not found").build();
        }
        updatedAuthor.setId(id);
        authors.put(id, updatedAuthor);
        return Response.ok(updatedAuthor).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        if (!authors.containsKey(id)) {
            return Response.status(Response.Status.NOT_FOUND).entity("Author not found").build();
        }
        authors.remove(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/{id}/books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooksByAuthor(@PathParam("id") int id) {
        if (!authors.containsKey(id)) {
            return Response.status(Response.Status.NOT_FOUND).entity("Author not found").build();
        }
        List<Book> authorBooks = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthorId() == id) {
                authorBooks.add(book);
            }
        }
        return Response.ok(authorBooks).build();
    }
}