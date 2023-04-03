package com.example.team_project.domain.domain.order.item.service;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.item.domain.OrderToProduct;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.order.list.service.OrderListAddService;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderCreateService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderListRepository orderListRepository;
    private final OrderListAddService orderListAddService;

    /**
     * 생성자를 통해 주문을 생성하여 주문상태를 ORDERD 로 표시,
     * 유저와 주문리스트 번호 가지고 저장,
     * 상품 재고 부족시 상품 소진 알리는 익셉션 발생
     **/
    public void create(Long userId, Long productId, int quantity, UserAddress userAddress, String paymentMethod) {
        User user = userRepository.validateUserId(userId);
        OrderList orderList = findAvailableOrderList(userId, userAddress, paymentMethod);
        Product product = productRepository.validateProductId(productId);

        OrderToProduct orderToProduct = new OrderToProduct(product, quantity);

        Order order = new Order(user, orderList, orderToProduct);
        orderRepository.save(order);
    }

    /**
     * 오더를 생성하려면 오더리스트를 필요로 합니다
     * 오더리스트 존재하지 않을시 오더리스트 서비스로
     * 이동후 생성후 다음 로직 진행
     **/
    private OrderList findAvailableOrderList(Long userId, UserAddress userAddress, String paymentMethod) {
        return orderListRepository.findByUserIdAndStatus(userId, true).orElseGet(() ->
                orderListAddService.add(userId, userAddress, paymentMethod)
        );
    }

}
