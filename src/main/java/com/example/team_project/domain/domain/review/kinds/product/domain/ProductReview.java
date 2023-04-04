package com.example.team_project.domain.domain.review.kinds.product.domain;

import com.example.team_project.domain.domain.product.product.domain.Product;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ProductReview {
    //리뷰의 고유번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 리뷰가 달린 상품의 정보
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    public ProductReview(){}

    public ProductReview(Product product){
        this.product=product;
    }

}
