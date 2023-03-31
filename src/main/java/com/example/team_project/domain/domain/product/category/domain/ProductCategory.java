package com.example.team_project.domain.domain.product.category.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name="productCategory")
@Getter
public class ProductCategory {

    //product 고유번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //품목 카테고리
    @Column
    private String categoryName;

    public ProductCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public ProductCategory(){

    }


}
