package com.irostub.learnspringbootjpa.api;

import com.irostub.learnspringbootjpa.domain.Address;
import com.irostub.learnspringbootjpa.domain.Order;
import com.irostub.learnspringbootjpa.domain.OrderItem;
import com.irostub.learnspringbootjpa.domain.OrderStatus;
import com.irostub.learnspringbootjpa.repository.OrderRepository;
import com.irostub.learnspringbootjpa.repository.order.query.OrderDirectQueryDto;
import com.irostub.learnspringbootjpa.repository.order.query.OrderFlatDto;
import com.irostub.learnspringbootjpa.repository.order.query.OrderItemDirectQueryDto;
import com.irostub.learnspringbootjpa.repository.order.query.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.*;
import static org.hibernate.Hibernate.initialize;

@RequiredArgsConstructor
@RestController
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;


    /**
     * ENTITY 를 사용한 조회 ~V3.1 까지
     */
    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAll();
        for (Order order : all) {
            initialize(order.getMember());
            initialize(order.getDelivery());
            order.getOrderItems().forEach(oi -> initialize(oi.getItem()));
        }
        return all;
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        return orderRepository.findAll().stream()
                .map(OrderDto::new).collect(toList());
    }

    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3() {
        return orderRepository.findAllWithAllRelation().stream()
                .map(OrderDto::new).collect(toList());
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3WithPaging(
            @RequestParam(defaultValue = "0") int pagingStart,
            @RequestParam(defaultValue = "100") int pagingEnd) {
        return orderRepository.findAllWithMemberAndDeliveryPaging(pagingStart, pagingEnd).stream()
                .map(OrderDto::new).collect(toList());
    }

    /**
     * DTO 를 통한 조회 ~V6 까지
     */
    @GetMapping("/api/v4/orders")
    public List<OrderDirectQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findAllWithAllRelationDirect();
    }

    @GetMapping("/api/v5/orders")
    public List<OrderDirectQueryDto> ordersV5() {
        return orderSimpleQueryRepository.findOrderItemByOrderId();
    }

    @GetMapping("/api/v6/orders")
    public List<OrderDirectQueryDto> ordersV6(){
        List<OrderFlatDto> flats = orderSimpleQueryRepository.findAllByDto_flat();
        return flats.stream()
                .collect(groupingBy(o -> new OrderDirectQueryDto(o.getOrderId(),
                                o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                        mapping(o -> new OrderItemDirectQueryDto(o.getOrderId(),
                                o.getItemName(), o.getOrderPrice(), o.getCount()), toList())
                )).entrySet().stream()
                .map(e -> new OrderDirectQueryDto(e.getKey().getOrderId(),
                        e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(),
                        e.getKey().getAddress(), e.getValue()))
                .collect(toList());
    }

    @Data
    private static class OrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus status;
        private Address address;
        private List<OrderItemDto> orderItems;

        public OrderDto(Order o) {
            this.orderId = o.getId();
            this.name = o.getMember().getName();
            this.orderDate = o.getOrderDate();
            this.status = o.getStatus();
            this.address = o.getDelivery().getAddress();
            this.orderItems = o.getOrderItems().stream()
                    .map(OrderItemDto::new)
                    .collect(toList());
        }
    }

    @Data
    private static class OrderItemDto {
        private String itemName;
        private Integer orderPrice;
        private Integer count;

        public OrderItemDto(OrderItem orderItem) {
            this.itemName = orderItem.getItem().getName();
            this.orderPrice = orderItem.getOrderPrice();
            this.count = orderItem.getCount();
        }
    }
}
