package com.example.team_project.domain.domain.order.list.service;

import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.item.service.OrderCancelService;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.enums.OrderStatus;
import com.example.team_project.exception.CannotCancelOrderException;
import com.example.team_project.exception.OrderListNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderListCancelServiceImpl implements OrderListCancelService {

    private final OrderRepository orderRepository;
    private final OrderListRepository orderListRepository;

    @Override
    public void cancel(Long orderListId) {
        List<Order> cancelableOrders = orderRepository.findCancelableOrders(orderListId);
        List<Order> notCancelableOrders = orderRepository.findNotCancelableOrders(orderListId);

        if (!validateNotCancelableOrders(notCancelableOrders)) {
            throw new CannotCancelOrderException();
        }

        cancelableOrders.forEach(order ->
                order.getOrderToProduct().updateStatus(OrderStatus.CANCELED));
        updateOrderListStatusToCanceled(orderListId);
    }

    private void updateOrderListStatusToCanceled(Long orderListId) {
        OrderList orderList = orderListRepository.findById(orderListId).orElseThrow(OrderListNotFoundException::new);
        orderList.updateStatus();
    }

    /**
     * 취소 불가능한 주문들이 없으면(비어있으면) true, 취소 불가능한 주문들이 있으면 false
     **/
    private boolean validateNotCancelableOrders(List<Order> notCancelableOrders) {
        return notCancelableOrders.isEmpty();
    }
}
