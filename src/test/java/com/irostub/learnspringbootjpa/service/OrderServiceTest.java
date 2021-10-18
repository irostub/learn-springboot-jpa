package com.irostub.learnspringbootjpa.service;

import com.irostub.learnspringbootjpa.domain.Address;
import com.irostub.learnspringbootjpa.domain.Member;
import com.irostub.learnspringbootjpa.domain.item.Book;
import com.irostub.learnspringbootjpa.domain.item.Item;
import com.irostub.learnspringbootjpa.repository.ItemRepository;
import com.irostub.learnspringbootjpa.repository.MemberRepository;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ItemRepository itemRepository;


    @Test
    @DisplayName("주문 성공")
    @Transactional
    void order() {
        //give
        Member member = new Member();
        member.setName("irostub");
        member.setAddress(new Address("seoul", "street", "10000"));

        Book book = new Book();
        book.setName("Steins; Gate");
        book.setPrice(1975);
        book.setStockQuantity(2010);
        book.setAuthor("5pb.");
        book.setIsbn("IBM5100");

        memberRepository.save(member);
        itemRepository.save(book);

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), 100);

        //then
        assertNotNull(orderId);
    }

    @Test
    @DisplayName("주문 실패 - 재고 수량 부족")
    void orderFail() {

    }

    @Test
    @DisplayName("주문 취소")
    void cancelOrder() {

    }

    @Test
    @DisplayName("주문 취소 실패 - 배송 완료 상태tion")
    void cancelOrderFail() {

    }
}