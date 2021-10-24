package com.irostub.learnspringbootjpa.api;

import com.irostub.learnspringbootjpa.controller.form.OrderSearch;
import com.irostub.learnspringbootjpa.domain.Address;
import com.irostub.learnspringbootjpa.domain.Order;
import com.irostub.learnspringbootjpa.domain.OrderStatus;
import com.irostub.learnspringbootjpa.repository.OrderRepository;
import com.irostub.learnspringbootjpa.repository.order.query.OrderSimpleDirectDto;
import com.irostub.learnspringbootjpa.repository.order.query.OrderSimpleQueryRepository;
import com.irostub.learnspringbootjpa.service.OrderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class OrderSimpleApiController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    //애초에 엔티티를 반환하지 말자
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findBySearchConditionWithCriteria(new OrderSearch());
        for (Order order : all) {
            Hibernate.initialize(order.getMember());
            Hibernate.initialize(order.getDelivery());
        }
        return all;
    }

    //N+1 문제가 발생한다. 실행 쿼리 1번 + 결과에 대한 쿼리 N번
    @GetMapping("/api/v2/simple-orders")
    public List<OrderSimpleDto> orderV2() {
        return orderRepository.findAll().stream()
                .map(OrderSimpleDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v3/simple-orders")
    public List<OrderSimpleDto> orderV3() {
        return orderRepository.findAllWithMemberAndDelivery().stream()
                .map(OrderSimpleDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleDirectDto> orderV4() {
        return orderSimpleQueryRepository.findAllWithDirect();
    }

    @Data
    private static class OrderSimpleDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public OrderSimpleDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();
        }
    }
}
