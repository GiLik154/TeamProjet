package com.example.team_project.domain.product.domain.category;

import com.example.team_project.domain.product.domain.Product;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name="productCategory")
@Getter
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    //product 고유번호
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product productId;

    //품목 카테고리
    @Column
    private String productCategory;

    public ProductCategory(){

    }


}
