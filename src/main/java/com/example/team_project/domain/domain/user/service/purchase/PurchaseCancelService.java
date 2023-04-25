package com.example.team_project.domain.domain.user.service.purchase;

import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseCancelService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderListRepository orderListRepository;


//    @Transactional
//    public void cancelOrder(Long orderId) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new OrderNotFoundException("주문을 찾을 수 없습니다. 주문 ID: " + orderId));
//
//        order.cancel();
//
//        List<OrderToProduct> orderToProducts = order.getOrderToProduct();
//        for (OrderToProduct orderToProduct : orderToProducts) {
//            Product product = orderToProduct.getProduct();
//            int orderCount = orderToProduct.getOrderCount();
//            product.increaseStock(orderCount);
//        }
//    }
}