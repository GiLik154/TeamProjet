package com.example.team_project.domain.domain.order.list.service;

import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.enums.OrderStatus;
import com.example.team_project.exception.CannotCancelOrderException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderListCancelService {

    private final OrderRepository orderRepository;


    /**
     * findCancelableOrders 로 취소 가능한 주문들을 리스트로 받아온 뒤
     * validateCancelableOrders 통해 취소 가능한 주문이 없다면 true, 있다면 false 를 반환
     * true 의 경우 취소 할 수 있는 주문이 없다고 익셉션이 발생한다
     * false 의 경우 forEach 를 통해 order 의 List 를 순회하며 상태를 CANCELED 로 바꿔준다
     **/
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
