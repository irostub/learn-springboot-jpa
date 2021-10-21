package com.irostub.learnspringbootjpa.controller.form;

import com.irostub.learnspringbootjpa.domain.item.Book;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class BookCreateForm {
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
}
