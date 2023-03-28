package com.example.team_project.domain.product.service.dto;

import com.example.team_project.domain.product.domain.Product;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
public class ProductStockDto {
   
    //productStock 고유번호
    private Long stockId;
    //product 고유번호
    private Product productId;
    //품목 가격
    int productPrice;
    //재고
    private int productStock;

}
