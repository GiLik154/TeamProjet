package com.example.team_project.domain.domain.order.item.domain;

import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.enums.OrderStatus;
import com.example.team_project.exception.InvalidQuantityException;
import com.example.team_project.exception.OutOfStockException;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class OrderToProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int totalPrice;

    protected OrderToProduct() {
    }

    /**
     * 생성시 주문 개수, 상품 재고 검사 하고 동시에 기본상태가 enum 을 통해 ORDERED 로 저장된다
     **/
    public OrderToProduct(Product product, int quantity) {
        validateQuantityAndProductStock(product, quantity);
        this.product = product;
        this.status = OrderStatus.ORDERED;
        this.quantity = quantity;
        this.totalPrice = product.getPrice() * quantity;
    }

    /**
     * 주문 상품 또는 개수 변경시 주문 개수, 상품 재고 검사 후 상품, 개수에 따른 총 가격 저장
     **/
    public void update(Product product, int quantity) {
        validateQuantityAndProductStock(product, quantity);
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = product.getPrice() * quantity;
    }

    /**
     * 사용자의 주문 개수와 상품의 재고 개수 검사 ->
     * 사용자가 주문 개수를 0개 이하로 입력시
     * 주문 개수 오류 익셉션 발생,
     * 주문 재고가 부족 할 시
     * 주문 재고 부족 익셉션 발생
     **/
    private void validateQuantityAndProductStock(Product product, int quantity) {
        if (quantity <= 0 || quantity > 1000000) {
            throw new InvalidQuantityException();
        } else if (product.getStock() < quantity) {
            throw new OutOfStockException();
        }
    }

    /**
     * 주문 상태 변경
     **/
    public void updateStatus(OrderStatus status) {
        this.status = OrderStatus.valueOf(status.name());
    }

    /**
     * 주문 상품 상태 취소로 변환
     **/
    public void cancel(Long id) {
        this.status = OrderStatus.CANCELED;
    }

}
