package com.example.team_project.domain.domain.order.list.service;

import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
@RequiredArgsConstructor
public class OrderStatusUpdateForPaymentsServiceImpl implements OrderStatusUpdateForPaymentsService {

    private final OrderRepository orderRepository;
    private final OrderListRepository orderListRepository;

    @Override
    public void update(Long orderListId) {
        orderListRepository.findById(orderListId)
                .ifPresent(orderList -> updateStatus(orderListId, orderList));
    }

    /**
     * 오더리스트안의 모든 상품들이 결제완료 상태가 되면 오더리스트 결제상태를 표시함
     **/
    private void updateStatus(Long orderListId, OrderList orderList) {
        if (isAllOrdersPaid(orderListId)) {
            orderList.updatePaymentStatus();
        }
    }

    private boolean isAllOrdersPaid(Long orderListId) {
        return orderRepository.findListByOrderListId(orderListId).stream().allMatch(order ->
                order.getOrderToProduct().getStatus() == OrderStatus.PAYMENT_COMPLETED);
    }
}
