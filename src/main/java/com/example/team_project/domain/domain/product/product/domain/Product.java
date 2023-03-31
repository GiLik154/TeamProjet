package com.example.team_project.domain.domain.product.product.domain;

import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.product.likecount.domain.ProductLike;
import com.example.team_project.domain.domain.product.sales.domain.ProductSales;
import com.example.team_project.domain.domain.product.stock.domain.ProductStock;
import com.example.team_project.domain.domain.shop.shop.domain.Shop;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "product")
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
    private String name;

    //품목이미지
    @Column
    private String image;

    //상세설명
    @Column
    private String description;

    private int price;

    private int stock;

    @OneToOne(fetch = FetchType.LAZY)
    private ProductSales productSales;

    @OneToOne(fetch = FetchType.LAZY)
    private ProductLike productLike;

    @OneToOne(fetch = FetchType.LAZY)
    private ProductCategory category;

    //
    public Product() {
    }

    public Product(String name, String image, String description, ProductCategory category, int price) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.category = category;
        this.price = price;
    }
}
