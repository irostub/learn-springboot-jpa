package com.irostub.learnspringbootjpa.domain;

import lombok.*;

import javax.persistence.*;

@SequenceGenerator(
        name = "ITEM_CATEGORY_SEQ_GENERATOR",
        sequenceName = "ITEM_CATEGORY_SEQ"
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
public class ItemCategory {
    @Setter(AccessLevel.PRIVATE)
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_CATEGORY_SEQ_GENERATOR")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    public void setCategory(Category category) {
        this.category = category;
        category.getItemCategories().add(this);
    }

    public void setItem(Item item) {
        this.item = item;
        item.getItemCategories().add(this);
    }

    //ITEM CATEGORY 편의 메서드
    public void addItemCategory(Category category, Item item) {
        this.category = category;
        this.item = item;

        category.getItemCategories().add(this);
        item.getItemCategories().add(this);
    }
}
