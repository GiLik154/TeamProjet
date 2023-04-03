package com.example.team_project.domain.domain.product.category.domain;

import com.example.team_project.enums.ProductCategoryStatus;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ProductCategory {

    //product 고유번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING) // CategoryStatus enum 타입으로 저장
    @Column(nullable = false)
    private ProductCategoryStatus status;

    public ProductCategory(ProductCategoryStatus status) {
        this.status = status;

    }

    protected ProductCategory(){}
}
