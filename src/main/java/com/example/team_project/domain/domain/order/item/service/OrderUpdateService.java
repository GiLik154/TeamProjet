package com.example.team_project.domain.domain.order.item.service;

import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.product.domain.Product;
import com.example.team_project.domain.domain.product.domain.ProductRepository;
import com.example.team_project.enums.OrderStatus;
import com.example.team_project.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderUpdateService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    /**
     * productId 와 orderId로 validate를 통해 객체를 얻어온 뒤
     * 상품 재고량 확인 부족할 시 재고량 소진 익셉션
     * 충분 할 시 입력값으로 상품, 개수 수정
     **/
    public void update(Long productId, Long orderId, int quantity) {

        Product product = productRepository.validateProductId(productId);
        Order order = orderRepository.validateOrderId(orderId);

        order.getOrderToProduct().update(product, quantity);
    }

    /**
     * 사용자와 주문아이디로 주문을 찾고 없을시
     * 주문을 찾을 수 없다는 익셉션 발생,
     * 주문이 존재 할 시 OrderToProduct 엔티티의
     * updateStatus 생성자를 통해
     * 주문의 상태를 변경해 줄 수 있다
     **/
    public void updateStatus(Long userId, Long orderId, OrderStatus orderStatus) {

        Optional<Order> order = orderRepository.findByUserIdAndId(userId, orderId);
        if (order.isEmpty()) {
            throw new OrderNotFoundException();
        }

        order.get().getOrderToProduct().updateStatus(orderStatus);
    }

}
