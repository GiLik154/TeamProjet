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
    private User user;

    /**
     * OrderList 안에는
     * 여러개의 Order 가 포함 될 수 있으니
     * manyToOne
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderList orderList;

    /**
     * (cascade = CascadeType.PERSIST)을 붙여줌으로써 Order 와 OrderToProduct 는 서로 함께 저장, 삭제 됩니다
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    private OrderToProduct orderToProduct;

    /**
     * Protected ->
     * 해당 클래스의 내부 로직을
     * 외부에서 직접적으로 조작하지 못하게 하여
     * 안정성을 높이기 위함입니다
     **/
    protected Order() {
    }

    /**
     * Order 를 생성 할 때
     * User, OrderList,OrderToProduct 를 가지고 생성되며
     * OrderToProduct 에는 주문 상품의 정보가 있습니다
     */
    public Order(User user, OrderList orderList, OrderToProduct orderToProduct) {
        this.user = user;
        this.orderList = orderList;
        this.orderToProduct = orderToProduct;
    }


}
