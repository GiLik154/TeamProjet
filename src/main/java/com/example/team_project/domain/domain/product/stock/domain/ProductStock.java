package com.example.team_project.domain.domain.product.stock.domain;

import com.example.team_project.domain.domain.product.product.domain.Product;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "ProductStock")
@Getter
public class ProductStock {

    //product 고유번호
    @Id
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;



    //품목 가격
    @Column
    int price;

    //재고
    @Column
    private int stock;

    public ProductStock() {
    }

    public ProductStock(Product product, int price, int stock) {
        this.product = product;
        this.price = price;
        this.stock = stock;
    }


}

