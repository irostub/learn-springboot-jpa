package com.irostub.learnspringbootjpa.controller.form;

import lombok.Data;

@Data
public class OrderForm {
    private Long memberId;
    private Long itemId;
    private Integer count;
}
