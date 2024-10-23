package api;

import dto.BookDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BooksClient {

    private static BooksClient INSTANCE;
    private final RequestSpecification requestSpecification;
    private static final String BOOKS_ENDPOINT = "/books";

    private BooksClient() {
        this.requestSpecification = RestAssured.given()
                .auth().preemptive().basic("user4", "hlB5U1rA")
                .baseUri("http://77.102.250.113:17354")
                .basePath("/api/v1")
                .log().all()
                .contentType(ContentType.JSON);
    }

    public static BooksClient getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BooksClient();
        }

        return INSTANCE;
    }

    public Response getBook(int id) {
        return this.requestSpecification.get(BOOKS_ENDPOINT + "/" + id);
    }

    public Response getBooks() {
        return this.requestSpecification.get(BOOKS_ENDPOINT);
    }

    public Response createBook(BookDto bookDto) {
        return this.requestSpecification.body(bookDto).post(BOOKS_ENDPOINT);
    }

    public Response updateBook(int id, BookDto bookDto) {
        return this.requestSpecification.body(bookDto).put(BOOKS_ENDPOINT + "/" + id);
    }

    public Response deleteBook(int id) {
        return this.requestSpecification.delete(BOOKS_ENDPOINT + "/" + id);
    }


}
