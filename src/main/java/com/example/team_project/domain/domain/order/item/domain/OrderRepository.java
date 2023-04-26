package com.example.team_project.domain.domain.order.item.domain;

import com.example.team_project.exception.OrderNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * orderId를 통해 주문을 optional객체로 받는다
     * 만약 객체가 존재한다면 존재하는 객체를 리턴받고 존재하지 않을시 OrderNotFoundException을 발생시킨다
     *
     * @param orderId: 주문 고유 ID
     * @return orderId를 통해 찾은 order객체
     * @throws OrderNotFoundException: 해당 주문을 찾을 수 없을 때
     **/
    default Order validateUserOrder(Long userId, Long orderId) {
        Optional<Order> orderOptional = findByUserIdAndId(userId, orderId);
        if (orderOptional.isPresent()) {
            return orderOptional.get();
        }
        throw new OrderNotFoundException();
    }

    /**
     * orderId를 통해 주문의 존재유무를 true 또는 false로 반환
     *
     * @param orderId: 주문 고유 ID
     * @return 주문이 존재할시 true 없을시 false
     **/
    boolean existsByUserIdAndId(Long userId, Long orderId);

    /**
     * userId를 통해 order를 찾아 optional객체로 리턴받는다
     *
     * @param userId: 사용자 고유 Id
     * @return 해당 user 고유 Id를 통해 찾은 order객체
     */
    Optional<Order> findByUserId(Long userId);

    /**
     * 같은 주문리스트 안에서 각 주문 상태가 SHIPPED, DELIVERED가 아닌 것들을 찾습니다(취소가 가능한 상품의 상태인것들)
     *
     * @param orderListId: 주문리스트의 고유 Id
     * @return 주문의 status(상태)가 ORDERED, PAYMENT_COMPLETED, PREPARING_FOR_SHIPPING인 order객체들의 List
     **/
    @Query("SELECT o FROM Order o WHERE o.orderList.id = :orderListId " +
            "AND o.orderToProduct.status != 'SHIPPED' " +
            "or o.orderToProduct.status != 'DELIVERED' ")
    List<Order> findCancelableOrders(@Param("orderListId") Long orderListId);

    @Query("SELECT o FROM Order o WHERE o.orderList.id = :orderListId " +
            "AND (o.orderToProduct.status = 'SHIPPED' " +
            "or o.orderToProduct.status = 'DELIVERED') ")
    List<Order> findShippedAndDeliveredOrders(@Param("orderListId") Long orderListId);

    @Query("SELECT o FROM Order o WHERE o.orderList.id = :orderListId " +
            "AND (o.orderToProduct.status = 'SHIPPED' " +
            "or o.orderToProduct.status = 'DELIVERED') ")
    List<Order> findNotCancelableOrders(@Param("orderListId") Long orderListId);

    /**
     * 같은 주문리스트 안에서 각 주문 상태가 PAYMENT_COMPLETED 인것을 찾습니다
     *
     * @param orderListId: 주문리스트의 고유 Id
     * @return 주문의 status(상태)가 PAYMENT_COMPLETED인 order객체들의 List
     **/
    @Query("SELECT o FROM Order o where o.orderList.id = :orderListId " +
            "AND o.orderToProduct.status = 'PAYMENT_COMPLETED'")
    List<Order> findPaymentCompletedOrders(@Param("orderListId") Long orderListId);

    @Query("SELECT o FROM Order o where o.user.id = :userId " +
            "AND o.orderToProduct.status = 'ORDERED' ORDER BY o.id DESC")
    List<Order> findByStatusOrderedAndUserId(@Param("userId") Long userId);

    @Query("SELECT o FROM Order o where o.orderList.id = :orderListId " +
            "AND o.orderToProduct.status = 'CANCELED'")
    List<Order> findCanceledOrdersInOrderList(@Param("orderListId") Long orderListId);

    /**
     * userId와 orderId를 통해 해당 Id값을 가진 order객체를 optional로 리턴받습니다
     *
     * @param userId:  사용자 고유 Id
     * @param orderId: 주문 고유 Id
     * @return userId와 orderId값으로 찾은 order객체
     **/
    Optional<Order> findByUserIdAndId(Long userId, Long orderId);

    /**
     * orderListId를 통해 해당 Id값을 가진 order객체들의 List를 optional로 리턴받습니다
     *
     * @param orderListId: 주문리스트의 고유 Id
     * @return 해당 orderListId를 가진 order객체들의 List
     **/

    Optional<Order> findByOrderListId(Long orderListId);

    List<Order> findListByOrderListId(Long orderListId);

    List<Order> findByUserIdAndOrderListId(Long userId, Long orderListId);

    Page<Order> findByOrderListId(Long orderListId, PageRequest id);//todo 페이징처리

}