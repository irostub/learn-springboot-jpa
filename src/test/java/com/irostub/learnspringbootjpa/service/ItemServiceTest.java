package com.irostub.learnspringbootjpa.service;

import com.irostub.learnspringbootjpa.domain.Album;
import com.irostub.learnspringbootjpa.domain.Item;
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
        Item item = new Album("irostub", "album");
        Long saveItemId = itemService.saveItem(item);
        assertNotNull(saveItemId);
    }

    @Test
    @DisplayName("아이템 전체 조회")
    void findItems() {
        Item item1 = new Album("irostub", "album");
        itemService.saveItem(item1);

        Item item2 = new Album("iro", "bumal");
        itemService.saveItem(item2);

        List<Item> items = itemService.findItems();

        assertThat(items).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    @DisplayName("아이템 단일 조회")
    void findOne() {
        Item item1 = new Album("irostub", "album");
        Long saveItemId = itemService.saveItem(item1);

        Item findItem = itemService.findOne(saveItemId);

        assertNotNull(findItem);
    }
}