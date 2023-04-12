package com.example.team_project.domain.domain.order.item.service;

import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderCancelServiceImpl implements OrderCancelService{

    private final OrderRepository orderRepository;

    @Override
    public void cancel(Long orderToProductId, Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new OrderNotFoundException();
        }

        Order order = orderRepository.validateOrderId(orderId);
        order.getOrderToProduct().cancel(orderToProductId);

    }
}
