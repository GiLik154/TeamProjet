package com.example.team_project.domain.domain.product.stock.service.dto;

import com.example.team_project.domain.domain.product.product.domain.Product;
import lombok.Getter;

@Getter
public class ProductStockDto {
    //품목 가격
    int productPrice;
    //재고
    private int productStock;

    public ProductStockDto(int productPrice, int productStock) {
        this.productPrice = productPrice;
        this.productStock = productStock;
    }
}
