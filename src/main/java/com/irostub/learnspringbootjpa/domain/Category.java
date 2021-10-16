package com.irostub.learnspringbootjpa.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@SequenceGenerator(
        name = "CATEGORY_SEQ_GENERATOR",
        sequenceName = "CATEGORY_SEQ")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
public class Category {
    @Setter(AccessLevel.PRIVATE)
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CATEGORY_SEQ_GENERATOR")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<CategoryItem> categoryItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    public void addCategoryItems(CategoryItem categoryItem) {
        this.getCategoryItems().add(categoryItem);
        categoryItem.setCategory(this);
    }

    public void addChild(Category category) {
        this.getChild().add(category);
        category.setParent(this);
    }
}
