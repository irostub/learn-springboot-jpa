package com.irostub.learnspringbootjpa.controller.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MemberForm {
    @NotEmpty
    private String name;

    private String city;
    private String street;
    private String zipcode;
}
