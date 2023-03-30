package com.example.team_project.domain.domain.product.sales.domain;

import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.sales.service.dto.ProductSalesDto;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name="productSales")
@Getter
public class ProductSales {


    //product 고유번호
    @Id
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;




    //판매량
    @Column
    private int sales;

    public ProductSales(){

    }

    public ProductSales(Product product, int sales) {
        this.product = product;
        this.sales = sales;
    }

}
