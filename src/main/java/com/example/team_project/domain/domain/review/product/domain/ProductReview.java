package com.example.team_project.domain.domain.review.product.domain;

import com.example.team_project.domain.domain.product.domain.Product;
import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class

ProductReview extends BaseReview {
    //리뷰의 고유번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public ProductReview(){}
}
