package com.example.team_project.domain.domain.product.domain.category;

import com.example.team_project.domain.domain.product.domain.Product;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    //product 고유번호
    @ManyToOne(fetch = FetchType.LAZY)
    private Product productId;

    //품목 카테고리
    @Column
    private String productCategory;

    public ProductCategory() {}

    public ProductCategory(Product productId, String productCategory) {
        this.productId = productId;
        this.productCategory = productCategory;
    }
}
