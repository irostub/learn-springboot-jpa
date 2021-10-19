package com.irostub.learnspringbootjpa.domain.item;

import com.irostub.learnspringbootjpa.domain.CategoryItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
public class Book extends Item{
    private String author;
    private String isbn;

    public Book(Long id, String name, Integer price, Integer stockQuantity, List<CategoryItem> categoryItems, String author, String isbn) {
        super(id, name, price, stockQuantity, categoryItems);
        this.author = author;
        this.isbn = isbn;
    }
}
