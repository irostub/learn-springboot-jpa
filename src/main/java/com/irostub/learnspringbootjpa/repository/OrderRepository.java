package com.irostub.learnspringbootjpa.repository;

import com.irostub.learnspringbootjpa.domain.Order;
import com.irostub.learnspringbootjpa.controller.form.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderRepository {
    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long orderId) {
        return em.find(Order.class, orderId);
    }

    public List<Order> findAll() {
        String query = "select o from Order o";
        return em.createQuery(query, Order.class).getResultList();
    }

    public List<Order> findAllWithMemberAndDelivery() {
        String query = "select o from Order o" +
                " join fetch o.member" +
                " join fetch o.delivery";
        return em.createQuery(query, Order.class).getResultList();
    }

    //작성하다 정신놓을 뻔했다.
    public List<Order> findBySearchConditionWithStringType(OrderSearch orderSearch) {
        String query = "select o from Order o join Member m on o.member.id = m.id where m.name = :name";
        boolean isFirst = true;
        if (orderSearch.getOrderStatus() != null) {
            query+=" where o.status= :orderStatus";
            isFirst = false;
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if(isFirst){
                query+=" where";
            }else{
                query+=" and";
            }
            query += " m.name = :name";
        }
        TypedQuery<Order> middleQuery = em.createQuery(query, Order.class);
        if (orderSearch.getOrderStatus() != null) {
            middleQuery.setParameter("orderStatus", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            middleQuery.setParameter("name", orderSearch.getMemberName());
        }

        return middleQuery
                .setMaxResults(1000)
                .getResultList();
    }

    //코드를 보고 SQL 이 어떻게 나갈지 모르겠다. 아니 사실 작성하는 것도 어렵다.
    //실행해서 로그 봐야 알 수 있을 것 같다.
    public List<Order> findBySearchConditionWithCriteria(OrderSearch order) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Object, Object> m = o.join("member", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();

        if (order.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"), order.getOrderStatus());
            criteria.add(status);
        }

        if (StringUtils.hasText(order.getMemberName())) {
            Predicate name = cb.like(m.get("name"), "%"+order.getMemberName()+"%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        return em.createQuery(cq).setMaxResults(1000).getResultList();
    }
}
