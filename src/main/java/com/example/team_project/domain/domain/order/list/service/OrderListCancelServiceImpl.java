package com.example.team_project.domain.domain.order.list.service;

import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.item.service.OrderCancelService;
import com.example.team_project.enums.OrderStatus;
import com.example.team_project.exception.CannotCancelOrderException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderListCancelServiceImpl implements OrderListCancelService {

    private final OrderRepository orderRepository;


    @Override
    public void cancel(Long orderListId) {
        List<Order> cancelableOrders = orderRepository.findCancelableOrders(orderListId);

        if (validateCancelableOrders(cancelableOrders)) {
            throw new CannotCancelOrderException();
        }

        cancelableOrders.forEach(order ->
                order.getOrderToProduct().updateStatus(OrderStatus.CANCELED));
    }

    /**
     * 취소 가능한 주문들이 없으면(비어있으면) true, 취소 가능한 주문들이 있으면 false
     **/
    private boolean validateCancelableOrders(List<Order> cancelableOrders) {
        return cancelableOrders.isEmpty();
    }
}
