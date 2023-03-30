package com.example.team_project.domain.domain.product.domain.stock;

import com.example.team_project.domain.domain.product.domain.Product;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name="ProductStock")
@Getter
public class ProductStock {

    @Id    //product 고유번호
    private Long productId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product Product;

    //품목 가격
    @Column
    int productPrice;

    //재고
    @Column
    private int productStock;


    public ProductStock(){

    }

}

