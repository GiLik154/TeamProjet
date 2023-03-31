package com.example.team_project.domain.domain.order.item.service;

import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.item.domain.OrderToProduct;
import com.example.team_project.domain.domain.order.list.Address;
import com.example.team_project.domain.domain.order.list.AddressRepository;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.order.list.service.OrderListCancelService;
import com.example.team_project.domain.domain.product.domain.Product;
import com.example.team_project.domain.domain.product.domain.ProductRepository;
import com.example.team_project.domain.domain.product.domain.stock.ProductStock;
import com.example.team_project.domain.domain.shop.shop.domain.Shop;
import com.example.team_project.domain.domain.shop.shop.domain.ShopRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.OrderStatus;
import com.example.team_project.exception.CannotCancelOrderException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderCancelServiceTest {

    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;
    private final OrderCancelService orderCancelService;
    private final OrderListRepository orderListRepository;
    private final OrderListCancelService orderListCancelService;

    @Autowired
    public OrderCancelServiceTest(ShopRepository shopRepository,
                                  UserRepository userRepository,
                                  OrderRepository orderRepository,
                                  ProductRepository productRepository,
                                  AddressRepository addressRepository,
                                  OrderCancelService orderCancelService,
                                  OrderListCancelService orderListCancelService, OrderListRepository orderListRepository) {
        this.shopRepository = shopRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.addressRepository = addressRepository;
        this.orderCancelService = orderCancelService;
        this.orderListCancelService = orderListCancelService;
        this.orderListRepository = orderListRepository;
    }

    @Test
    void 주문상품_취소_정상작동() {
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
        productRepository.save(product);
        Long productId = product.getId();

        Address address = new Address();
        addressRepository.save(address);

        OrderList orderList = new OrderList(user, address, "카드");
        orderListRepository.save(orderList);

        OrderToProduct orderToProduct = new OrderToProduct(product, 10);

        Order order = new Order(user, orderList, orderToProduct);
        orderRepository.save(order);
        Long orderId = order.getId();
        Long orderToProductId = orderToProduct.getId();

        //when
        orderCancelService.cancel(orderToProductId, orderId);

        //then
        assertEquals("CANCELED", order.getOrderToProduct().getStatus().toString());
    }

    @Test
    void 주문상품_취소불가_정상작동() {
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
        productRepository.save(product);

        Address address = new Address();
        addressRepository.save(address);

        OrderList orderList = new OrderList(user, address, "카드");
        orderListRepository.save(orderList);
        Long orderListId = orderList.getId();

        OrderToProduct orderToProduct = new OrderToProduct(product, 10);

        Order order = new Order(user, orderList, orderToProduct);
        orderRepository.save(order);

        orderToProduct.updateStatus(OrderStatus.CANCELED);

        //when
        CannotCancelOrderException exception = assertThrows(CannotCancelOrderException.class, () ->
                orderListCancelService.cancel(orderListId)
        );

        //then
        assertEquals("This order cannot be canceled", exception.getMessage());
    }

    @Test
    void 주문상품_취소_상태변경_정상작동() {
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
                "product_name1",
                "product_image1",
                "product_description1",
                productStock);
        productRepository.save(product);
        productRepository.save(product1);

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
        orderToProduct1.updateStatus(OrderStatus.PREPARING_FOR_SHIPPING);

        //when
        orderListCancelService.cancel(orderListId);

        //then
        assertEquals("CANCELED", order.getOrderToProduct().getStatus().toString());
        assertEquals("CANCELED", order1.getOrderToProduct().getStatus().toString());
    }


}