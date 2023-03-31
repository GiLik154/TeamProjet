package com.example.team_project.domain.domain.order.list.service;

import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.list.Address;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.enums.OrderStatus;
import com.example.team_project.exception.OrderListNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderListUpdateService {

    private final OrderRepository orderRepository;
    private final OrderListRepository orderListRepository;


    /**
     * 주문리스트의 주소지 변경
     **/
    public void update(Long userId, Long orderListId, Address address) {

        Optional<OrderList> orderList = orderListRepository.findByUserIdAndId(userId, orderListId);
        if (orderList.isEmpty()) {
            throw new OrderListNotFoundException();
        }
        orderList.get().update(address);
    }

    /**
     * 같은 orderListId를 가진 주문들을 찾아 List로 받고 paymentStatus에서 stream으로 변환 후
     * allmatch로 order상태가 PAYMENT_COMPLETED와 같은지 비교한 뒤 리스트 안의 값이 맞을시
     * true를 반환한다. 후에 if문에서 paymentStatus가 true(리스트 안의 모든 주문이 결제 완료인 상태)라면
     * orderList의 상태를 update하여 false로 바꾸어 해당 orderList의 status를 false 변환
     **/
    public void paymentResult(Long orderListId) {
        List<Order> orders = orderRepository.findPaymentCompletedOrders(orderListId);
        OrderList orderList = findByOrderListById(orderListId);

        boolean paymentStatus = orders.stream().allMatch(order ->
                order.getOrderToProduct().getStatus() == OrderStatus.PAYMENT_COMPLETED);

        if (paymentStatus) {
            orderList.updateStatus();
        }
    }


    private OrderList findByOrderListById(Long orderListId) {
        return orderListRepository.findById(orderListId).orElseThrow(OrderListNotFoundException::new);
    }


}
