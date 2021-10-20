package com.irostub.learnspringbootjpa.service;

import com.irostub.learnspringbootjpa.domain.item.Album;
import com.irostub.learnspringbootjpa.domain.item.Book;
import com.irostub.learnspringbootjpa.domain.item.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Test
    @DisplayName("아이템 저장")
    void saveItem() {
        Book book = Book.builder()
                .name("irostub")
                .price(10000)
                .stockQuantity(1999)
                .author("stub")
                .isbn("10000")
                .build();
        Long saveItemId = itemService.saveItem(book.getName(),book.getPrice(),book.getStockQuantity(), book.getAuthor(), book.getIsbn());
        assertNotNull(saveItemId);
    }

    @Test
    @DisplayName("아이템 전체 조회")
    void findItems() {
        Book book = Book.builder()
                .name("irostub")
                .price(10000)
                .stockQuantity(1999)
                .author("stub")
                .isbn("10000")
                .build();
        itemService.saveItem(book.getName(),book.getPrice(),book.getStockQuantity(), book.getAuthor(), book.getIsbn());

        Book book2 = Book.builder()
                .name("irostub2")
                .price(1001)
                .stockQuantity(194)
                .author("stub2")
                .isbn("10001")
                .build();
        itemService.saveItem(book2.getName(),book2.getPrice(),book2.getStockQuantity(), book2.getAuthor(), book2.getIsbn());

        List<Item> items = itemService.findItems();

        assertThat(items).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    @DisplayName("아이템 단일 조회")
    void findOne() {
        Book book = Book.builder()
                .name("irostub")
                .price(10000)
                .stockQuantity(1999)
                .author("stub")
                .isbn("10000")
                .build();
        Long saveItemId = itemService.saveItem(book.getName(), book.getPrice(), book.getStockQuantity(), book.getAuthor(), book.getIsbn());

        Item findItem = itemService.findOne(saveItemId);

        assertNotNull(findItem);
    }
}