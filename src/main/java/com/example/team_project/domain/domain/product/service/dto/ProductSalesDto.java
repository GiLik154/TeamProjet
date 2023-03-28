package com.example.team_project.domain.domain.product.service.dto;

import com.example.team_project.domain.domain.product.domain.Product;
import lombok.Getter;

@Getter
public class ProductSalesDto {

    //productSales 고유번호
    private Long salesId;
    //product 고유번호
    private Product productId;
    //판매량
    private int productSales;

}
