package com.irostub.learnspringbootjpa.domain;

import lombok.*;

import javax.persistence.*;

@SequenceGenerator(
        name = "DELIVERY_SEQ_GENERATOR",
        sequenceName = "DELIVERY_SEQ"
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
public class Delivery {
    @Setter(AccessLevel.PRIVATE)
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELIVERY_SEQ_GENERATOR")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}
