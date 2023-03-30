package com.example.team_project.domain.domain.order.item.domain;

import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.user.domain.User;
import lombok.Getter;

import javax.persistence.*;

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
        this.totalPrice = product.getProductStock().getPrice() * quantity;
    }

    public void update(Product product, int quantity){
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = product.getProductStock().getPrice() * quantity;
    }


}
