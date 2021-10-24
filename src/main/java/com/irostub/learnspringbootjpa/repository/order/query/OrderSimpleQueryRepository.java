package com.irostub.learnspringbootjpa.repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

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
}
