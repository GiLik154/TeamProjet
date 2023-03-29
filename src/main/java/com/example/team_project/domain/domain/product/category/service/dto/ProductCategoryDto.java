package com.example.team_project.domain.domain.product.category.service.dto;

import com.example.team_project.domain.domain.product.product.domain.Product;
import lombok.Getter;

@Getter
public class ProductCategoryDto {

    //product 고유번호
    private Product productId;
    //품목 카테고리
    private String productCategory;

}
