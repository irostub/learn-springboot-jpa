package com.irostub.learnspringbootjpa.service;

import com.irostub.learnspringbootjpa.domain.*;
import com.irostub.learnspringbootjpa.domain.item.Book;
import com.irostub.learnspringbootjpa.domain.item.Item;
import com.irostub.learnspringbootjpa.excption.NotEnoughStockException;
import com.irostub.learnspringbootjpa.repository.ItemRepository;
import com.irostub.learnspringbootjpa.repository.MemberRepository;
import com.irostub.learnspringbootjpa.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    ItemRepository itemRepository;

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderService orderService;

    @Test
    @DisplayName("주문 성공")
    void order() {
        //given
        Member member = createMember();
        Item item = createItem();

        given(memberRepository.findOne(1L))
                .willReturn(member);
        given(itemRepository.findOne(1L))
                .willReturn(item);

        //when
        orderService.order(1L, 1L, 100);

        //then
        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
        then(orderRepository).should(times(1)).save(captor.capture());
    }

    @Test
    @DisplayName("주문 실패 - 재고 수량 부족")
    void orderFail(){
        //given
        Member member = createMember();
        Item item = createItem();

        given(memberRepository.findOne(1L))
                .willReturn(member);
        given(itemRepository.findOne(1L))
                .willReturn(item);

        //when
        Executable executable = () -> orderService.order(1L, 1L, 50000);

        //then
        assertThrows(NotEnoughStockException.class, executable);
    }

    private Item createItem() {
        return new Book(
                1L,
                "itemName",
                15000,
                2021,
                new ArrayList<>(),
                "5pg",
                "isbn5100");
    }

    private Member createMember() {
        return new Member(
                1L,
                "irostub",
                new Address("seoul", "street", "10000"),
                new ArrayList<>());
    }
}