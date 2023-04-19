package com.example.team_project.domain.domain.user.service.purchase;

import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CancelPurchaseServiceImpl implements CancelPurchaseService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    CancelPurchaseServiceImpl(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }
}
