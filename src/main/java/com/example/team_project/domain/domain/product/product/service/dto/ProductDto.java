package com.example.team_project.domain.domain.product.product.service.dto;

import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import lombok.Getter;

@Getter
public class ProductDto {

    //품목이름
    private String productName;
    //품목이미지
    private String productImage;
    //상세설명
    private String productDescription;

    private Seller seller;

    //재고
    private int productStock;
    //가격
    private int productPrice;

    //카테고리
    private String categoryDto;


    public ProductDto() {
    }

    public ProductDto(String productName, String productImage, String productDescription, int productPrice, int productStock, String categoryDto) {
        this.productName = productName;
        this.productImage = productImage;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.categoryDto = categoryDto;
    }


    public void update(String productName, String productImage, String productDescription, int productStock, int productPrice, ProductCategory category) {
        this.productName = productName;
        this.productImage = productImage;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.categoryDto = categoryDto;
    }
}
