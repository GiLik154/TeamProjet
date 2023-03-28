package com.example.team_project.domain.domain.product.service.dto;

import com.example.team_project.domain.domain.product.domain.Product;
import lombok.Getter;

@Getter
public class ProductCategoryDto {

    //productCateogry 고유번호
    private Long categoryId;
    //product 고유번호
    private Product productId;
    //품목 카테고리
    private String productCategory;

}
