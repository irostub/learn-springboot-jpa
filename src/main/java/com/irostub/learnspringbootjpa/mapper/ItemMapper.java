package com.irostub.learnspringbootjpa.mapper;

import com.irostub.learnspringbootjpa.controller.form.BookUpdateForm;
import com.irostub.learnspringbootjpa.domain.item.Book;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ItemMapper {
    void updateBookEntity(BookUpdateForm bookUpdateForm, @MappingTarget Book book);
}
