package com.irostub.learnspringbootjpa.domain.item;

import com.irostub.learnspringbootjpa.domain.CategoryItem;
import lombok.*;

import javax.persistence.Entity;
import java.util.List;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
@Entity
public class Book extends Item{
    @NonNull
    private String author;
    private String isbn;

    @Builder
    public Book(Long id, String name, Integer price, Integer stockQuantity, List<CategoryItem> categoryItems, String author, String isbn) {
        super(id, name, price, stockQuantity, categoryItems);
        this.author = author;
        this.isbn = isbn;
    }
}
