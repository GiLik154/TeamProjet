package com.example.team_project.domain.domain.product.domain;

import com.example.team_project.domain.domain.product.domain.category.ProductCategory;
import com.example.team_project.domain.domain.product.domain.likecount.ProductLike;
import com.example.team_project.domain.domain.product.domain.sales.ProductSales;
import com.example.team_project.domain.domain.product.domain.stock.ProductStock;
import com.example.team_project.domain.domain.shop.shop.domain.Shop;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name="product")
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


    //정규화 1:1연결
    @OneToOne(fetch = FetchType.LAZY)
    private ProductStock productStock;

    @OneToOne(fetch = FetchType.LAZY)
    private ProductSales productSales;

    @OneToOne(fetch = FetchType.LAZY)
    private ProductLike productLike;

    @OneToOne(fetch = FetchType.LAZY)
    private ProductCategory productCategory;
    //
    public Product(){

    }




}
