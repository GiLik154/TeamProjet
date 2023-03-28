package com.example.team_project.domain.user.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderlist_id")
    private OrderList orderList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    private int totalPrice;

    public Order() {
    }

    public Order(User user, OrderList orderList, Product product, int quantity) {
        this.user = user;
        this.orderList = orderList;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = product.getPrice() * quantity;
    }

    public void update(Product product, int quantity){
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = product.getPrice() * quantity;
    }


}
