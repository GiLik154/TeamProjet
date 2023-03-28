package com.example.team_project.domain.domain.product.domain.sales;

import com.example.team_project.domain.domain.product.domain.Product;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name="productSales")
@Getter
public class ProductSales {
    @Id
    private Long salesId;

    //product 고유번호
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product productId;

    //판매량
    @Column
    private int productSales;

    public ProductSales(){

    }

}
