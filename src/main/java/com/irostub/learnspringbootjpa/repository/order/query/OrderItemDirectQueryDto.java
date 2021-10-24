package com.irostub.learnspringbootjpa.repository.order.query;

import lombok.Data;

@Data
public class OrderItemDirectQueryDto {
    private Long orderId;
    private String itemName;
    private int orderPrice;
    private int count;
    public OrderItemDirectQueryDto(Long orderId, String itemName, int orderPrice, int
            count) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}
