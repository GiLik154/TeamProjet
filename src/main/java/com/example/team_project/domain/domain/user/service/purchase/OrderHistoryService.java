package com.example.team_project.domain.domain.user.service.purchase;

import com.example.team_project.domain.domain.order.item.domain.Order;

import java.util.List;

public interface OrderHistoryService {
    List<Order> getOrderHistory(Long userId, Order order);
}
