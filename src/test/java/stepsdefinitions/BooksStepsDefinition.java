package stepsdefinitions;

import api.BooksClient;
import dto.BookDto;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BooksStepsDefinition {

    private Response response;
    private int lastCreatedBookId;

    @When("I request all books")
    public void requestAllBooks() {
        this.response = BooksClient.getInstance().getBooks();
    }

    @When("I request book by id {int}")
    public void requestBook(int id) {
        this.response = BooksClient.getInstance().getBook(id);
    }

    @When("I create book [name={string}, author={string}, publication={string}, category={string}, pages={int}, price={double}]")
    public void createBook(String name, String author, String publication, String category, int pages, double price) {
        BookDto bookDto = new BookDto(name, author, publication, category, pages, price);

        this.response = BooksClient.getInstance().createBook(bookDto);
        this.lastCreatedBookId = response.as(BookDto.class).getId();
    }

    @When("I update book with id={int} [name={string}, author={string}, publication={string}, category={string}, " +
            "pages={int}, price={double}]")
    public void update(int id, String name, String author, String publication, String category, int pages, double price) {
        BookDto bookDto = new BookDto(name, author, publication, category, pages, price);
        this.response = BooksClient.getInstance().updateBook(id, bookDto);
    }

    @When("I update last created book [name={string}, author={string}, publication={string}, category={string}, " +
            "pages={int}, price={double}]")
    public void updateLastCreatedBook(String name, String author, String publication, String category,
                                      int pages, double price) {
        BookDto bookDto = new BookDto(name, author, publication, category, pages, price);
        this.response = BooksClient.getInstance().updateBook(lastCreatedBookId, bookDto);
    }

    @When("I delete book by id {int}")
    public void deleteBook(int id) {
        this.response = BooksClient.getInstance().deleteBook(id);
    }

    @When("I delete last book")
    public void deleteLastBook() {
        this.response = BooksClient.getInstance().deleteBook(lastCreatedBookId);
    }

    @Then("Books list should contain books")
    public void booksListShouldContainItems() {
        BookDto[] books = response.as(BookDto[].class);
        Assertions.assertTrue(books.length > 0, "Books list is empty");
    }

    @Then("Books list should contain book with name {string}")
    public void booksListShouldContainBookByName(String expectedBookName) {
        Optional<BookDto> bookOptional = findBookByName(expectedBookName);
        Assertions.assertTrue(bookOptional.isPresent(), "Book " + expectedBookName + " is not present");
    }

    @Then("Books list should not contain book with name {string}")
    public void booksListShouldNotContainBookByName(String expectedBookName) {
        Optional<BookDto> bookOptional = findBookByName(expectedBookName);
        Assertions.assertFalse(bookOptional.isPresent(), "Book " + expectedBookName + " is not present");
    }

    @Then("Book with id={int} should have name {string}")
    public void bookShouldHaveName(int id, String expectedBookName) {
        Optional<BookDto> bookOptional = findBookById(id);
        Assertions.assertTrue(bookOptional.isPresent(), "Book with id " + id + " is not present");
        Assertions.assertEquals(expectedBookName, bookOptional.get().getName(),
                "Book with id " + id + " has wrong name");
    }

    @Then("Last created book should have name {string}")
    public void lastCreatedBookShouldHaveName(String expectedBookName) {
        Optional<BookDto> bookOptional = findBookById(lastCreatedBookId);
        Assertions.assertTrue(bookOptional.isPresent(), "Book with id " + lastCreatedBookId + " is not present");
        Assertions.assertEquals(expectedBookName, bookOptional.get().getName(),
                "Book with id " + lastCreatedBookId + " has wrong name");
    }

    @Then("Status code should be {int}")
    public void statusCodeShouldBe(int statusCode) {
        assertEquals(statusCode, response.statusCode(), "Wrong status code");
    }

    private Optional<BookDto> findBookByName(String name) {
        return Stream.of(response.as(BookDto[].class))
                .filter(b -> Objects.equals(b.getName(), name))
                .findFirst();
    }

    private Optional<BookDto> findBookById(int id) {
        return Stream.of(response.as(BookDto[].class))
                .filter(b -> Objects.equals(b.getId(), id))
                .findFirst();
    }
}
