package com.example.team_project.domain.product.domain.sales;

import com.example.team_project.domain.product.domain.Product;
import com.example.team_project.domain.shop.domain.Shop;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name="productSales")
@Getter
public class productSales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salesId;

    //product 고유번호
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product productId;

    //판매량
    @Column
    private int productSales;

}
