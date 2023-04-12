package com.example.team_project.domain.domain.product.product.service.dto;

import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import lombok.Getter;

@Getter
public class ProductDto {

    //품목이름
    private String name;
    //품목이미지
    private String image;
    //상세설명
    private String description;

    //재고
    private Integer stock;
    //가격
    private Integer price;

    //카테고리
    private String categoryDto;





    public ProductDto(String name, String image, String description, Integer stock, Integer price, String categoryDto) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.categoryDto = categoryDto;
    }

    public void update(String name, String image, String description, int stock, int price) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.stock = stock;
        this.price = price;
    }
}
