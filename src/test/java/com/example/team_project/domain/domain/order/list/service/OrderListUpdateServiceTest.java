package com.example.team_project.domain.domain.order.list.service;

import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.item.domain.OrderToProduct;
import com.example.team_project.domain.domain.order.list.Address;
import com.example.team_project.domain.domain.order.list.AddressRepository;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.product.domain.Product;
import com.example.team_project.domain.domain.product.domain.ProductRepository;
import com.example.team_project.domain.domain.product.domain.stock.ProductStock;
import com.example.team_project.domain.domain.shop.shop.domain.Shop;
import com.example.team_project.domain.domain.shop.shop.domain.ShopRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderListUpdateServiceTest {

    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final OrderListRepository orderListRepository;
    private final OrderListUpdateService orderListUpdateService;

    @Autowired
    OrderListUpdateServiceTest(UserRepository userRepository,
                               ShopRepository shopRepository,
                               ProductRepository productRepository,
                               AddressRepository addressRepository,
                               OrderRepository orderRepository,
                               OrderListRepository orderListRepository,
                               OrderListUpdateService orderListUpdateService) {
        this.userRepository = userRepository;
        this.shopRepository = shopRepository;
        this.productRepository = productRepository;
        this.addressRepository = addressRepository;
        this.orderRepository = orderRepository;
        this.orderListRepository = orderListRepository;
        this.orderListUpdateService = orderListUpdateService;
    }


    @Test
    void 주문리스트_수정_주소지변경_정상작동() {
        //given
        User user = new User("test_name", "test_pw");
        userRepository.save(user);
        Long userId = user.getId();

        Address address = new Address("서울", "강남구");
        Address address1 = new Address("부천", "오정구");
        addressRepository.save(address);
        addressRepository.save(address1);

        OrderList orderList = new OrderList(user, address, "카드");
        orderListRepository.save(orderList);
        Long orderListId = orderList.getId();

        //when
        orderListUpdateService.update(userId, orderListId, address1);
        Optional<OrderList> orderListOptional = orderListRepository.findByUserId(userId);
        OrderList orderList1 = orderListOptional.get();

        //then
        assertEquals(orderList.getId(), orderList1.getId());
        assertEquals("부천", orderList1.getAddress().getCity());
        assertEquals("오정구", orderList1.getAddress().getDistrict());
    }

    @Test
    void 주문리스트_결제완료시_사용불가_정상작동() {
        //given
        User user = new User("test_user", "test_pw");
        userRepository.save(user);

        Shop shop = new Shop();
        shopRepository.save(shop);

        ProductStock productStock = new ProductStock(1000, 100);
        Product product = new Product(shop,
                "product_name",
                "product_image",
                "product_description",
                productStock);
        Product product1 = new Product(shop,
                "update_name",
                "update_image",
                "update_description",
                productStock);
        productRepository.save(product);
        productRepository.save(product1);
        Long productId1 = product1.getId();

        Address address = new Address();
        addressRepository.save(address);

        OrderList orderList = new OrderList(user, address, "카드");
        orderListRepository.save(orderList);
        Long orderListId = orderList.getId();

        OrderToProduct orderToProduct = new OrderToProduct(product, 10);
        OrderToProduct orderToProduct1 = new OrderToProduct(product1, 10);

        Order order = new Order(user, orderList, orderToProduct);
        Order order1 = new Order(user, orderList, orderToProduct1);
        orderRepository.save(order);
        orderRepository.save(order1);

        orderToProduct.updateStatus(OrderStatus.PAYMENT_COMPLETED);
        orderToProduct1.updateStatus(OrderStatus.PAYMENT_COMPLETED);

        //when
        orderListUpdateService.paymentResult(orderListId);

        //then
        assertFalse(orderList.isStatus());
    }
}