package com.example.team_project.domain.domain.order.list.domain;

import com.example.team_project.domain.domain.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderListRepository extends JpaRepository<OrderList, Long> {


    /**
     * userId와 status를 통해 orderList를 optional로 받는다
     *
     * @param userId: 사용자 고유 Id
     * @param status: 주문리스트의 사용 가능 여부 (가능시: true / 불가능시: false)
     **/
    Optional<OrderList> findByUserIdAndStatus(Long userId, boolean status);

    Optional<OrderList> findByUserIdAndId(Long userId, Long orderListId);

    List<OrderList> findByUserId(Long userId);

    List<OrderList> findByUserIdOrderByStatusDesc(Long userId);

}
