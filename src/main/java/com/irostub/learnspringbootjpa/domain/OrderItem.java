package com.irostub.learnspringbootjpa.domain;

import lombok.*;

import javax.persistence.*;

@SequenceGenerator(
        name = "ORDER_ITEM_SEQ_GENERATOR",
        sequenceName = "ORDER_ITEM_SEQ"
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
public class OrderItem {
    @Setter(AccessLevel.PRIVATE)
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_ITEM_SEQ_GENERATOR")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    private Integer orderPrice;
    private Integer count;

    public void setOrders(Orders orders){
        this.orders = orders;
        orders.getOrderItems().add(this);
    }
}
