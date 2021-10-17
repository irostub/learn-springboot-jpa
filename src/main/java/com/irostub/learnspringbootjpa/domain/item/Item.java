package com.irostub.learnspringbootjpa.domain.item;

import com.irostub.learnspringbootjpa.domain.CategoryItem;
import com.irostub.learnspringbootjpa.excption.NotEnoughStockException;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@SequenceGenerator(
        name = "ITEM_SEQ_GENERATOR",
        sequenceName = "ITEM_SEQ"
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Entity
public abstract class Item {
    @Setter(AccessLevel.PRIVATE)
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_SEQ_GENERATOR")
    private Long id;
    private String name;
    private Integer price;
    private Integer stockQuantity;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<CategoryItem> categoryItems = new ArrayList<>();

    public void addCategoryItems(CategoryItem categoryItem) {
        this.getCategoryItems().add(categoryItem);
        categoryItem.setItem(this);
    }

    public void removeStock(Integer count){
        int result = this.stockQuantity - count;
        if (result < 0) {
            throw new NotEnoughStockException();
        }
        this.stockQuantity = result;
    }

    public void addStock(Integer count) {
        this.stockQuantity += count;
    }
}
