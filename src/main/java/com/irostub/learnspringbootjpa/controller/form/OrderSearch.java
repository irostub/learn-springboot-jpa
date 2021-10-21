package com.irostub.learnspringbootjpa.controller.form;

import com.irostub.learnspringbootjpa.domain.OrderStatus;
import lombok.Data;

@Data
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;
}
