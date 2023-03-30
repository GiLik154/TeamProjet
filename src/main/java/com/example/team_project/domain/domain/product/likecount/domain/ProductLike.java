package com.example.team_project.domain.domain.product.likecount.domain;

import com.example.team_project.domain.domain.product.product.domain.Product;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name="ProductLike")
@Getter
public class ProductLike {

    //product 고유번호
    @Id
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;


    //좋아요 숫자
    @Column
    private String likeCount;

    public ProductLike(){

    }


    public ProductLike(Product product, String likeCount) {
        this.product = product;
        this.likeCount = likeCount;
    }
}

