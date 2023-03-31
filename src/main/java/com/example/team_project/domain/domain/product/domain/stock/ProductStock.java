package com.example.team_project.domain.domain.product.domain.stock;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ProductStock {

    @Id    //product 고유번호
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //품목 가격
    @Column
    int productPrice;

    //재고
    @Column
    private int productStock;


    public ProductStock(){
    }

    public ProductStock(int productPrice, int productStock){
        this.productPrice = productPrice;
        this.productStock = productStock;
    }


}

