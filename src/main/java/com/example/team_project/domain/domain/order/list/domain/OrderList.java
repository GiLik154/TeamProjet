package com.example.team_project.domain.domain.order.list.domain;

import com.example.team_project.domain.domain.order.list.Address;
import com.example.team_project.domain.domain.user.domain.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class OrderList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 하나의 유저가 다수의 OrderList를
     * 가지고 있을수 있으므로 manyToOne
     **/
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    /**
     * 주소
     **/
    @OneToOne(fetch = FetchType.LAZY)
    private Address address;

    /**
     * 결제 방법
     **/
    @Column(nullable = false)
    private String paymentMethod;

    private boolean status;

    protected OrderList() {
    }

    public OrderList(User user, Address address, String paymentMethod) {
        this.user = user;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.status = true;
    }

    public void update(Address address) {
        this.address = address;

    }

    public void updateStatus() {
        this.status = false;
    }
}
