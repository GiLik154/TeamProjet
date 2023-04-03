package com.example.team_project.domain.domain.order.list.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderListRepository extends JpaRepository<OrderList, Long> {

    Optional<OrderList> findByUserIdAndStatus(Long userId, boolean status);

    Optional<OrderList> findByUserIdAndId(Long userId, Long orderListId);

    Optional<OrderList> findByUserId(Long userId);


}
