package com.example.team_project.domain.domain.product.product.domain;

import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.product.product.service.dto.ProductDto;
import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.enums.ProductCategoryStatus;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //품목이름
    private String name;

    //판매자 고유번호
    @ManyToOne(fetch = FetchType.LAZY)
    private Seller seller;

    //품목이미지
    private String image;

    //상세설명
    private String description;

    private int stock;

    private int price;


    //정규화 1:1연결

    @OneToOne(fetch = FetchType.LAZY)
    private ProductCategory category;
    //
    private int salesCount;

    private int likeCount;

    public Product(){
    }

    public Product(String name, Seller seller, String image, String description, int stock, int price, ProductCategory category) {
        this.name = name;
        this.seller = seller;
        this.image = image;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.category = category;
        this.likeCount=0;
        this.salesCount=0;
    }








    public void updateSalesCount(Long orderId){
        this.salesCount++;
    }

    public void updateLikeCount(){
        this.likeCount++;
    }


    public void update(String name, String image, String description, int stock, int price, ProductCategory category) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.category = category;
    }


}
