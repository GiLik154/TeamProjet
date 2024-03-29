package com.example.team_project.domain.domain.order.item.domain;

import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCoupon;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.user.domain.User;
import lombok.Getter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private OrderList orderList;

    /**
     * (cascade = CascadeType.PERSIST)을 붙여줌으로써 Order 와 OrderToProduct 는 서로 함께 저장, 삭제 됩니다
     */
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private OrderToProduct orderToProduct;

    @OneToOne
    private UserCoupon userCoupon;

    /**
     * Protected ->
     * 해당 클래스의 내부 로직을
     * 외부에서 직접적으로 조작하지 못하게 하여
     * 안정성을 높이기 위함입니다
     **/
    protected Order() {
    }

    public Order(User user, OrderList orderList, OrderToProduct orderToProduct) {
        this.user = user;
        this.orderList = orderList;
        this.orderToProduct = orderToProduct;
    }

    public void couponUpdate(UserCoupon userCoupon) {
        this.userCoupon = userCoupon;
    }
    public int getTotalPrice() {
        return this.orderToProduct.getTotalPrice();
    }

}
