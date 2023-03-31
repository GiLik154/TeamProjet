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
public class OrderCancelService {

    private final OrderRepository orderRepository;


    /**
     * 주문 상품 취소시 없는 주문이면 주문이 없다는 익셉션 발생,
     * 정상적으로 취소 될시 주문상품 상태가 CANCELED로 바뀜
     **/
    public void cancel(Long orderToProductId, Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new OrderNotFoundException();
        }

        Order order = orderRepository.validateOrderId(orderId);
        order.getOrderToProduct().cancel(orderToProductId);

    }
}
