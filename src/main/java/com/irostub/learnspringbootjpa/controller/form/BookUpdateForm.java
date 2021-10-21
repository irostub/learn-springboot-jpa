package com.irostub.learnspringbootjpa.controller.form;

import com.irostub.learnspringbootjpa.domain.item.Book;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data @NoArgsConstructor
public class BookUpdateForm {
    private Long id;

    @NotEmpty
    private String name;

    @Min(0)
    @NotNull
    private Integer price;

    @Min(0)
    @NotNull
    private Integer stockQuantity;

    private String author;

    @NotEmpty
    private String isbn;

    public BookUpdateForm(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.price = book.getPrice();
        this.stockQuantity = book.getStockQuantity();
        this.author = book.getAuthor();
        this.isbn = book.getIsbn();
    }
}
