package com.example.team_project.domain.domain.order.list.domain;

import com.example.team_project.domain.domain.order.list.Address;
import com.example.team_project.domain.domain.user.domain.User;
<<<<<<< HEAD
=======
import com.example.team_project.enums.OrderStatus;
>>>>>>> e97cbad6628652ecf9dd4df84b65861ffe514662
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
<<<<<<< HEAD
=======
@Table(name = "order_lists")
>>>>>>> e97cbad6628652ecf9dd4df84b65861ffe514662
public class OrderList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

<<<<<<< HEAD
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
=======
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

    private double totalPrice;

    public OrderList(){}

    public OrderList(User user, String paymentMethod){
        this.user = user;
        this.paymentMethod = paymentMethod;
        this.status = OrderStatus.ORDERED;
    }

    public void cancel(User user){
        this.user = user;
        this.status = OrderStatus.CANCELED;
>>>>>>> e97cbad6628652ecf9dd4df84b65861ffe514662
    }
}
