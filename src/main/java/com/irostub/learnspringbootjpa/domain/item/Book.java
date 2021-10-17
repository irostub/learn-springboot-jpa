package com.irostub.learnspringbootjpa.domain.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
public class Book extends Item{
    private String author;
    private String isbn;
}
