package com.example.team_project.domain.domain.product.service.dto;

import com.example.team_project.domain.domain.product.domain.Product;
import lombok.Getter;

@Getter
public class ProductStockDto {
   
    //product 고유번호
    private Product productId;
    //품목 가격
    int productPrice;
    //재고
    private int productStock;

}
