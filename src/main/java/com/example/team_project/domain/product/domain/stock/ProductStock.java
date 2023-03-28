package com.example.team_project.domain.product.domain.stock;

import com.example.team_project.domain.product.domain.Product;
import com.example.team_project.domain.shop.domain.Shop;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name="ProductStock")
@Getter
public class ProductStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;


    //product 고유번호
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product productId;


    //품목 가격
    @Column
    int productPrice;

    //재고
    @Column
    private int productStock;


    public ProductStock(){

    }

}

