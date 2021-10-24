package com.irostub.learnspringbootjpa.repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Repository
public class OrderSimpleQueryRepository {

    private final EntityManager em;

    public List<OrderSimpleDirectDto> findAllWithDirect() {
        String query = "select new com.irostub.learnspringbootjpa.repository.order.query.OrderSimpleDirectDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                " from Order o" +
                " join o.member m" +
                " join o.delivery d";
        return em.createQuery(query, OrderSimpleDirectDto.class).getResultList();
    }

    public List<OrderDirectQueryDto> findAllWithAllRelationDirect() {

        List<OrderDirectQueryDto> findOrder = findOrders();
        findOrder.forEach(
                o->o.setOrderItems(findOrderItem(o.getOrderId()))
        );
        return findOrder;
    }

    private List<OrderItemDirectQueryDto> findOrderItem(Long orderId) {
        String query = "select new com.irostub.learnspringbootjpa.repository.order.query.OrderItemDirectQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                " from OrderItem oi" +
                " join oi.item i" +
                " where oi.id=:id";
        return em.createQuery(query, OrderItemDirectQueryDto.class).setParameter("id", orderId).getResultList();
    }

    private List<OrderDirectQueryDto> findOrders() {
        return em.createQuery("select new com.irostub.learnspringbootjpa.repository.order.query.OrderDirectQueryDto(o.id, m.name, o.orderDate, o.status, d.address) " +
                "from Order o " +
                "join o.member m " +
                "join o.delivery d", OrderDirectQueryDto.class).getResultList();
    }

    //N+1 문제를 벗어나 1+1 로 해결하는 방식
    public List<OrderDirectQueryDto> findOrderItemByOrderId() {
        List<OrderDirectQueryDto> orders = findOrders();
        Map<Long,List<OrderItemDirectQueryDto>> orderItems = findOrderItems(toOrderIds(orders));
        orders.forEach(o-> o.setOrderItems(orderItems.get(o.getOrderId())));
        return orders;
    }

    private Map<Long,List<OrderItemDirectQueryDto>> findOrderItems(List<Long> orderIds) {
        List<OrderItemDirectQueryDto> orderItems = em.createQuery("select new com.irostub.learnspringbootjpa.repository.order.query.OrderItemDirectQueryDto(oi.order.id, oi.item.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi" +
                        " join oi.item" +
                        " where oi.order.id in :orderIds", OrderItemDirectQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();
        return orderItems.stream().collect(Collectors.groupingBy(OrderItemDirectQueryDto::getOrderId));
    }

    private List<Long> toOrderIds(List<OrderDirectQueryDto> orders) {
        return orders.stream().map(OrderDirectQueryDto::getOrderId).collect(Collectors.toList());
    }

    //N+1 문제를 벗어나 1 로 해결할 수 있지만 JOIN 연산이 많아지므로 성능 측정 필요
    public List<OrderFlatDto> findAllByDto_flat() {
        return em.createQuery("select new com.irostub.learnspringbootjpa.repository.order.query.OrderFlatDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count)" +
                " from Order o" +
                " join o.member m" +
                " join o.delivery d" +
                " join o.orderItems oi" +
                " join oi.item i", OrderFlatDto.class)
                .getResultList();
    }
}
