package com.example.team_project.domain.domain.order.item.domain;

import com.example.team_project.exception.OrderNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    default Order validateOrderId(Long orderId) {
        Optional<Order> orderOptional = findById(orderId);
        if (orderOptional.isPresent()) {
            return orderOptional.get();
        }
        throw new OrderNotFoundException();
    }

    boolean existsById(Long orderId);

    Optional<Order> findByUserId(Long userId);

    /**
     * 같은 주문리스트 안에서 각 주문 상태가 SHIPPED, DELIVERED, CANCELED 가 아닌 것들을 찾습니다
     **/
    @Query("SELECT o FROM Order o WHERE o.orderList.id = :orderListId " +
            "AND o.orderToProduct.status != 'SHIPPED' " +
            "AND o.orderToProduct.status != 'DELIVERED' " +
            "AND o.orderToProduct.status != 'CANCELED'")
    List<Order> findCancelableOrders(@Param("orderListId") Long orderListId);

    /**
     * 같은 주문리스트 안에서 각 주문 상태가 PAYMENT_COMPLETED 인것을 찾습니다
     **/
    @Query("SELECT o FROM Order o where o.orderList.id = :orderListId " +
            "AND o.orderToProduct.status = 'PAYMENT_COMPLETED'")
    List<Order> findPaymentCompletedOrders(@Param("orderListId") Long orderListId);

    Optional<Order> findByUserIdAndId(Long userId, Long OrderId);


}
