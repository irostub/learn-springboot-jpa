package com.irostub.learnspringbootjpa.mapper;

import com.irostub.learnspringbootjpa.controller.form.BookUpdateForm;
import com.irostub.learnspringbootjpa.domain.item.Book;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-21T17:47:46+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.9 (Oracle Corporation)"
)
@Component
public class ItemMapperImpl implements ItemMapper {

    @Override
    public void updateBookEntity(BookUpdateForm bookUpdateForm, Book book) {
        if ( bookUpdateForm == null ) {
            return;
        }

        if ( bookUpdateForm.getId() != null ) {
            book.setId( bookUpdateForm.getId() );
        }
        if ( bookUpdateForm.getName() != null ) {
            book.setName( bookUpdateForm.getName() );
        }
        if ( bookUpdateForm.getPrice() != null ) {
            book.setPrice( bookUpdateForm.getPrice() );
        }
        if ( bookUpdateForm.getStockQuantity() != null ) {
            book.setStockQuantity( bookUpdateForm.getStockQuantity() );
        }
        if ( bookUpdateForm.getAuthor() != null ) {
            book.setAuthor( bookUpdateForm.getAuthor() );
        }
        if ( bookUpdateForm.getIsbn() != null ) {
            book.setIsbn( bookUpdateForm.getIsbn() );
        }
    }
}
