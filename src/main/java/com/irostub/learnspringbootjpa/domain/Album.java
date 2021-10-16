package com.irostub.learnspringbootjpa.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
public class Album extends Item{
    private String artist;
    private String etc;
}
