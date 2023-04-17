package com.example.team_project.domain.domain.user.service.purchase;

import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.list.domain.OrderList;

public interface CancelPurchaseService {
    void cancelPurchase(Long userId, Order order, OrderList orderList);
}
