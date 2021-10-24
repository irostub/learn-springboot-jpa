package com.irostub.learnspringbootjpa.repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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
}
