package com.example.team_project.domain.domain.product.product.domain;

import com.example.team_project.domain.domain.image.ImageUpload;
import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import lombok.Getter;

import javax.persistence.*;
import java.awt.*;

@Entity
@Getter
public class Product implements ImageUpload {
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

    private Integer stock;

    private Integer price;


    //정규화 1:1연결

    @OneToOne(fetch = FetchType.LAZY)
    private ProductCategory category;

    //



    private int salesCount;

    private int likeCount;

    protected Product(){
    }

    public Product(String name, Seller seller, String description, int stock, int price, ProductCategory category) {
        this.name = name;
        this.seller = seller;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.category = category;
        this.likeCount=0;
        this.salesCount=0;
    }



    //판매량
    public void updateSalesCount(){
        this.salesCount++;
    }

    
    //좋아요수
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

    
    //재고량
    public void decreaseSalesCount(Long orderId){
        this.salesCount--;
    }

    @Override
    public void uploadImage(String image) {
        this.image=image;
    }
}
