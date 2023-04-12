package com.example.team_project.domain.domain.order.list.domain;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.payment.domain.Payment;
import com.example.team_project.domain.domain.user.domain.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class OrderList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    private UserAddress userAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    private Payment payment;

    private LocalDateTime orderDate;

    private boolean status;

    protected OrderList() {
    }

    public OrderList(User user, UserAddress userAddress, Payment payment, LocalDateTime orderDate) {
        this.user = user;
        this.userAddress = userAddress;
        this.payment = payment;
        this.orderDate = orderDate;
        this.status = true;
    }

    public void update(UserAddress userAddress) {
        this.userAddress = userAddress;

    }

    public void updateStatus() {
        this.status = false;
    }
}
