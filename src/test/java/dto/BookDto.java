package dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {
    private Integer id;
    private String name;
    private String author;
    private String publication;
    private String category;
    private Integer pages;
    private Double price;

    public BookDto(String name, String author, String publication, String category, int pages, double price) {
        this.name = name;
        this.author = author;
        this.publication = publication;
        this.category = category;
        this.pages = pages;
        this.price = price;
    }
}
