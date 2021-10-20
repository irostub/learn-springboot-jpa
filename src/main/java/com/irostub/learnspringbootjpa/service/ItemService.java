package com.irostub.learnspringbootjpa.service;

import com.irostub.learnspringbootjpa.controller.form.BookUpdateForm;
import com.irostub.learnspringbootjpa.domain.item.Book;
import com.irostub.learnspringbootjpa.domain.item.Item;
import com.irostub.learnspringbootjpa.mapper.ItemMapper;
import com.irostub.learnspringbootjpa.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    @Transactional
    public Long saveItem(String name, Integer price, Integer stockQuantity, String author, String isbn) {
        Item item = Book.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .author(author)
                .isbn(isbn)
                .build();
        itemRepository.save(item);
        return item.getId();
    }

    @Transactional
    public void updateItem(Long id, BookUpdateForm bookUpdateForm) {
        Item item = itemRepository.findOne(id);
        itemMapper.updateBookEntity(bookUpdateForm, (Book)item);
    }
}
