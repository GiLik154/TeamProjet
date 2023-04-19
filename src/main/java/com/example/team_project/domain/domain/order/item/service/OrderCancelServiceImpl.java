package com.example.team_project.domain.domain.order.item.service;

import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.enums.OrderStatus;
import com.example.team_project.exception.CannotCancelOrderException;
import com.example.team_project.exception.OrderListNotFoundException;
import com.example.team_project.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderCancelServiceImpl implements OrderCancelService {

    private final OrderRepository orderRepository;
    private final OrderListRepository orderListRepository;

    @Override
    public void cancel(Long orderToProductId, Long orderId, Long orderListId) {

        if (!orderRepository.existsById(orderId)) {
            throw new OrderNotFoundException();
        }

        Order order = validateCancelableOrder(orderId);
        order.getOrderToProduct().cancel(orderToProductId);
        cancelOrderListOnAllItemsCancelled(orderListId);
    }

    /**
     * 취소 가능 상품 검증하기
     **/
    private Order validateCancelableOrder(Long orderId) {
        Order order = orderRepository.validateOrderId(orderId);
        String orderStatus = order.getOrderToProduct().getStatus().toString();

        if (orderStatus.equals("CANCELED") || orderStatus.equals("DELIVERED") || orderStatus.equals("SHIPPED")) {
            throw new CannotCancelOrderException();
        }

        return order;
    }

    private void cancelOrderListOnAllItemsCancelled(Long orderListId) {
        List<Order> orders = orderRepository.findListByOrderListId(orderListId);
        OrderList orderList = orderListRepository.findById(orderListId).orElseThrow(OrderListNotFoundException::new);

        boolean orderStatus = orders.stream().allMatch(order ->
                order.getOrderToProduct().getStatus() == OrderStatus.CANCELED);

        if (orderStatus) {
            orderList.updateStatus();
        }
    }
}
