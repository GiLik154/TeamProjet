package com.example.team_project.domain.domain.order.item.service;

import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.enums.OrderStatus;
import com.example.team_project.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderUpdateServiceImpl implements OrderUpdateService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;


    @Override
    public void update(Long productId, Long orderId, int quantity) {

        Product product = productRepository.validateProductId(productId);
        Order order = orderRepository.validateOrderId(orderId);

        order.getOrderToProduct().update(product, quantity);
    }

    /**
     * 사용자의 고유 ID와 주문의 고유 ID로 주문을 찾는다
     * 해당하는 주문을 찾을 수 없을시 OrderNotFoundException 발생
     * 해당하는 주문을 찾았을시 OrderToProduct Entity의 updateStatus 메소드를 통해 주문의 상태를 변경해 줄 수 있다
     **/
    public void updateStatus(Long userId, Long orderId, OrderStatus orderStatus) {

        Optional<Order> order = orderRepository.findByUserIdAndId(userId, orderId);
        if (order.isEmpty()) {
            throw new OrderNotFoundException();
        }

        order.get().getOrderToProduct().updateStatus(orderStatus);
    }

}
