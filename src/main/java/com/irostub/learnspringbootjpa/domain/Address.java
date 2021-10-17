package com.irostub.learnspringbootjpa.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class Address {
    private String city;
    private String street;
    private String zipcode;
}
