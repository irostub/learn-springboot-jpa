package com.irostub.learnspringbootjpa.repository;

import com.irostub.learnspringbootjpa.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RequiredArgsConstructor
@Repository
@Transactional(readOnly = true)
public class ItemRepository {
    @PersistenceContext
    private final EntityManager em;

    public Long save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
        return item.getId();
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        String query = "select i from Item i";
        return em.createQuery(query, Item.class).getResultList();
    }
}
