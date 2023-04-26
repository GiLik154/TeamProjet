package com.example.team_project.domain.domain.order.item.service;

import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.item.domain.OrderToProduct;
import com.example.team_project.domain.domain.order.list.service.OrderListUpdateService;
import com.example.team_project.domain.domain.order.list.service.OrderStatusUpdateForPaymentsService;
import com.example.team_project.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderCompletionServiceImpl implements OrderCompletionService {
    private final OrderRepository orderRepository;
    private final OrderListUpdateService orderListUpdateService;
    private final OrderStatusUpdateForPaymentsService orderStatusUpdateForPaymentsService;

    @Override
    public void processOrderPayment(Long userId, Long userAddressId, Long paymentId, Long orderListId) {
        updateOrderListInfo(userId, orderListId, userAddressId, paymentId);
        updateAllOrdersToPaid(userId, orderListId);
        orderStatusUpdateForPaymentsService.update(orderListId);
        updateProductSalesAndStock(orderListId);
    }

    /**
     * 해당 주문리스트안의 주문들 상태들 모두 결제완료상태로 업데이트 함
     **/
    private void updateAllOrdersToPaid(Long userId, Long orderListId) {
        orderRepository.findByUserIdAndOrderListId(userId, orderListId).forEach(order ->
                order.getOrderToProduct().updateStatus(OrderStatus.PAYMENT_COMPLETED));

    }

    private void updateOrderListInfo(Long userId, Long orderListId, Long userAddressId, Long paymentId) {
        orderListUpdateService.update(userId, orderListId, userAddressId, paymentId);
    }

    private void updateProductSalesAndStock(Long orderListId) {
        orderRepository.findListByOrderListId(orderListId).forEach(order -> {
            OrderToProduct orderToProduct = order.getOrderToProduct();
            orderToProduct.getProduct().increaseSalesCountAndDecreaseStock(orderToProduct.getQuantity());
        });
    }

}
