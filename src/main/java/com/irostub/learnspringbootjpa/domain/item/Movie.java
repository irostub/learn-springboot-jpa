package com.irostub.learnspringbootjpa.domain.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
public class Movie extends Item{
    private String director;
    private String actor;
}
