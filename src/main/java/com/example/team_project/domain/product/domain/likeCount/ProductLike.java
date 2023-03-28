package com.example.team_project.domain.product.domain.likeCount;

import com.example.team_project.domain.product.domain.Product;
import com.example.team_project.domain.shop.domain.Shop;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name="ProductLike")
@Getter
public class ProductLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    //product 고유번호
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product productId;

    @Column
    private String likeCount;

}

