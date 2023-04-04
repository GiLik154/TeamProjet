package com.example.team_project.domain.domain.user.service.purchase;

import com.example.team_project.domain.domain.order.item.domain.Order;

import java.util.List;

public interface UserPurchaseHistoryService {
    List<Order> getPurchaseHistoryList(String userId);
}
