package com.irostub.learnspringbootjpa.repository.order.query;

import com.irostub.learnspringbootjpa.domain.Address;
import com.irostub.learnspringbootjpa.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDirectQueryDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDirectQueryDto> orderItems;
    public OrderDirectQueryDto(Long orderId, String name, LocalDateTime orderDate,
                         OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}
