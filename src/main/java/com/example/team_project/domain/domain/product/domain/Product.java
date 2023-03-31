package com.example.team_project.domain.domain.product.domain;

import com.example.team_project.domain.domain.product.domain.stock.ProductStock;
import com.example.team_project.domain.domain.shop.shop.domain.Shop;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //판매자 고유번호
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shopId;

    //품목이름
    @Column
    private String productName;

    //품목이미지
    @Column
    private String productImage;

    //상세설명
    @Column
    private String productDescription;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private ProductStock productStock;


    protected Product(){
    }


    public Product(Shop shopId, String productName, String productImage, String productDescription, ProductStock productStock) {
        this.shopId = shopId;
        this.productName = productName;
        this.productImage = productImage;
        this.productDescription = productDescription;
        this.productStock = productStock;
    }
}
