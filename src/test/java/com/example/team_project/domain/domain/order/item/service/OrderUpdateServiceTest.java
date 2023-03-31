package com.example.team_project.domain.domain.order.item.service;

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
import com.example.team_project.exception.InvalidQuantityException;
import com.example.team_project.exception.OutOfStockException;
import com.example.team_project.exception.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderUpdateServiceTest {

    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;
    private final OrderListRepository orderListRepository;
    private final OrderRepository orderRepository;
    private final OrderUpdateService orderUpdateService;

    @Autowired
    public OrderUpdateServiceTest(UserRepository userRepository,
                                  ShopRepository shopRepository,
                                  ProductRepository productRepository,
                                  AddressRepository addressRepository,
                                  OrderListRepository orderListRepository,
                                  OrderRepository orderRepository,
                                  OrderUpdateService orderUpdateService) {
        this.userRepository = userRepository;
        this.shopRepository = shopRepository;
        this.productRepository = productRepository;
        this.addressRepository = addressRepository;
        this.orderListRepository = orderListRepository;
        this.orderRepository = orderRepository;
        this.orderUpdateService = orderUpdateService;
    }

    @Test
    void 주문상품_수정_정상작동() {
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

        OrderToProduct orderToProduct = new OrderToProduct(product, 10);

        Order order = new Order(user, orderList, orderToProduct);
        orderRepository.save(order);
        Long orderId = order.getId();

        //when
        orderUpdateService.update(productId1, orderId, 20);

        //then
        assertNotNull(orderId);
        assertEquals("update_name", order.getOrderToProduct().getProduct().getProductName());
        assertEquals("update_image", order.getOrderToProduct().getProduct().getProductImage());
        assertEquals("update_description", order.getOrderToProduct().getProduct().getProductDescription());
        assertEquals(20, order.getOrderToProduct().getQuantity());
    }

    @Test
    void 주문상품_수정_주문개수0이하_비정상작동() {
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

        OrderToProduct orderToProduct = new OrderToProduct(product, 10);

        Order order = new Order(user, orderList, orderToProduct);
        orderRepository.save(order);
        Long orderId = order.getId();

        //when
        InvalidQuantityException exception = assertThrows(InvalidQuantityException.class, () ->
                orderUpdateService.update(productId1, orderId, 0)
        );

        //then
        assertEquals("Please enter the correct number", exception.getMessage());
    }

    @Test
    void 주문상품_수정_상품재고소진_비정상작동() {
        //given
        User user = new User("test_user", "test_pw");
        userRepository.save(user);

        Shop shop = new Shop();
        shopRepository.save(shop);

        ProductStock productStock = new ProductStock(1000, 100);
        ProductStock productStock1 = new ProductStock(1000, 0);
        Product product = new Product(shop,
                "product_name",
                "product_image",
                "product_description",
                productStock);
        Product product1 = new Product(shop,
                "update_name",
                "update_image",
                "update_description",
                productStock1);
        productRepository.save(product);
        productRepository.save(product1);
        Long productId1 = product1.getId();

        Address address = new Address();
        addressRepository.save(address);

        OrderList orderList = new OrderList(user, address, "카드");
        orderListRepository.save(orderList);

        OrderToProduct orderToProduct = new OrderToProduct(product, 10);

        Order order = new Order(user, orderList, orderToProduct);
        orderRepository.save(order);
        Long orderId = order.getId();

        //when
        OutOfStockException exception = assertThrows(OutOfStockException.class, () ->
                orderUpdateService.update(productId1, orderId, 10)
        );

        //then
        assertEquals("This product is sold out", exception.getMessage());
    }

    @Test
    void 주문상품_수정_유효하지_않는_상품_비정상작동() {
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

        //when
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () ->
                orderUpdateService.update(productId + 1L, orderId, 10)
        );

        //then
        assertEquals("This product does not exist", exception.getMessage());
    }

    @Test
    void 주문상품_상태수정_정상작동() {
        //given
        User user = new User("test_user", "test_pw");
        userRepository.save(user);
        Long userId = user.getId();

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

        OrderToProduct orderToProduct = new OrderToProduct(product, 10);

        Order order = new Order(user, orderList, orderToProduct);
        orderRepository.save(order);
        Long orderId = order.getId();

        //when
        orderUpdateService.updateStatus(userId, orderId, OrderStatus.PAYMENT_COMPLETED);

        //then
        assertEquals("PAYMENT_COMPLETED", order.getOrderToProduct().getStatus().toString());

    }
}