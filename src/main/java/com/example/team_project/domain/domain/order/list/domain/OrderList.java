package com.example.team_project.domain.domain.order.list.domain;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.payment.domain.Payment;
import com.example.team_project.domain.domain.user.domain.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

    private LocalDate orderDate;

    private boolean status = true;

    private boolean paymentsStatus = true;

    protected OrderList() {
    }

    public OrderList(User user, UserAddress userAddress, Payment payment, LocalDate orderDate) {
        this.user = user;
        this.userAddress = userAddress;
        this.payment = payment;
        this.orderDate = orderDate;
    }

    public OrderList(User user, LocalDate orderDate) {
        this.user = user;
        this.orderDate = orderDate;
    }

    public OrderList(User user, Payment payment) {
        this.user = user;
        this.payment = payment;
    }

    public void update(UserAddress userAddress) {
        this.userAddress = userAddress;
    }

    public void update(UserAddress userAddress, Payment payment) {
        this.userAddress = userAddress;
        this.payment = payment;
    }

    public void updateStatus() {
        this.status = false;
    }

    public void updatePaymentStatus() {
        this.paymentsStatus = false;
    }
}
