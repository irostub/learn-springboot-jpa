package com.irostub.learnspringbootjpa.domain;

import com.irostub.learnspringbootjpa.domain.item.Item;
import lombok.*;

import javax.persistence.*;

@SequenceGenerator(
        name = "ITEM_CATEGORY_SEQ_GENERATOR",
        sequenceName = "ITEM_CATEGORY_SEQ"
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
public class CategoryItem {
    @Setter(AccessLevel.PRIVATE)
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_CATEGORY_SEQ_GENERATOR")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;
}
