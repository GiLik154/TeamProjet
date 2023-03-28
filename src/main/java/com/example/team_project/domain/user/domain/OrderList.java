package com.example.team_project.domain.user.domain;

import com.example.team_project.domain.user.enums.OrderStatus;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "order_lists")
public class OrderList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address")
    private Address address;

    @Column(nullable = false)
    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "order_status")
    private OrderStatus status;

    @OneToMany(mappedBy = "order_list")
    private List<Order> orders = new ArrayList<>();

    private double totalPrice;



    public OrderList(){}

    public OrderList(User user, Address address, String paymentMethod){
        this.user = user;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.status = OrderStatus.ORDERED;
    }

    public void cancel(User user){
        this.user = user;
        this.status = OrderStatus.CANCELED;
    }

    public double getTotalPrice() {
        double totalPrice = 0.0;
        for (Order order : orders) {
            totalPrice += order.getTotalPrice();
        }
        return totalPrice;
    }







}
