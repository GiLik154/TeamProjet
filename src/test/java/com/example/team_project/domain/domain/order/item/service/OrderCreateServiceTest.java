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
import com.example.team_project.exception.InvalidQuantityException;
import com.example.team_project.exception.OutOfStockException;
import com.example.team_project.exception.ProductNotFoundException;
import com.example.team_project.exception.UserNotFoundException;
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
class OrderCreateServiceTest {

    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;
    private final OrderCreateService orderCreateService;
    private final OrderListRepository orderListRepository;

    @Autowired
    OrderCreateServiceTest(
            OrderCreateService orderCreateService,
            UserRepository userRepository,
            OrderRepository orderRepository,
            ProductRepository productRepository,
            OrderListRepository orderListRepository,
            AddressRepository addressRepository,
            ShopRepository shopRepository
    ) {
        this.orderCreateService = orderCreateService;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderListRepository = orderListRepository;
        this.addressRepository = addressRepository;
        this.shopRepository = shopRepository;
    }

    @Test
    void 주문추가_정상작동() {
        //given
        User user = new User("test_name", "test_pw");
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
        Long productId = product.getId();

        Address address = new Address();
        addressRepository.save(address);

        //when
        orderCreateService.create(userId, productId, 10, address, "카드");
        Optional<Order> orderOptional = orderRepository.findByUserId(userId);
        Order order = orderOptional.get();

        //then
        assertNotNull(order.getId());
        assertEquals(userId, order.getUser().getId());
        assertEquals(productId, order.getOrderToProduct().getProduct().getId());
        assertEquals("카드", order.getOrderList().getPaymentMethod());
        assertEquals("product_name", order.getOrderToProduct().getProduct().getProductName());
        assertEquals("product_image", order.getOrderToProduct().getProduct().getProductImage());
        assertEquals("product_description", order.getOrderToProduct().getProduct().getProductDescription());
    }

    @Test
    void 주문추가_유효하지_않은_사용자_비정삭작동() {
        //given
        User user = new User("test_name", "test_pw");
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
        Long productId = product.getId();

        Address address = new Address();
        addressRepository.save(address);

        OrderList orderList = new OrderList(user, address, "카드");
        orderListRepository.save(orderList);
        Long orderListId = orderList.getId();

        //when
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                orderCreateService.create(userId + 1L, productId, 10, address, "카드")
        );

        //then
        assertEquals("This user could not be found", exception.getMessage());
    }

    @Test
    void 주문추가_가격확인_정상작동() {
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
                "product_name",
                "product_image",
                "product_description",
                productStock);
        productRepository.save(product);
        productRepository.save(product1);

        Address address = new Address();
        addressRepository.save(address);

        OrderList orderList = new OrderList(user, address, "카드");
        orderListRepository.save(orderList);

        OrderToProduct orderToProduct = new OrderToProduct(product, 10);
        OrderToProduct orderToProduct1 = new OrderToProduct(product1, 20);

        Order order = new Order(user, orderList, orderToProduct);
        Order order1 = new Order(user, orderList, orderToProduct1);
        orderRepository.save(order);
        orderRepository.save(order1);

        //then
        assertEquals(10000, orderToProduct.getTotalPrice());
        assertEquals(20000, orderToProduct1.getTotalPrice());

    }

    @Test
    void 주문추가_유효하지_않는_상품_비정상작동() {
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
        Long productId = product.getId();

        Address address = new Address();
        addressRepository.save(address);

        OrderList orderList = new OrderList(user, address, "카드");
        orderListRepository.save(orderList);
        Long orderListId = orderList.getId();

        OrderToProduct orderToProduct = new OrderToProduct(product, 10);

        Order order = new Order(user, orderList, orderToProduct);
        orderRepository.save(order);

        //when
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () ->
                orderCreateService.create(userId, productId + 1L, 10, address, "카드")
        );

        //then
        assertEquals("This product does not exist", exception.getMessage());
    }

    @Test
    void 주문추가_주문개수0이하_비정상작동() {
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
        Long productId = product.getId();

        Address address = new Address();
        addressRepository.save(address);

        OrderList orderList = new OrderList(user, address, "카드");
        orderListRepository.save(orderList);
        Long orderListId = orderList.getId();

        //when
        InvalidQuantityException exception = assertThrows(InvalidQuantityException.class, () ->
                orderCreateService.create(userId, productId, 0, address, "카드")
        );

        //then
        assertEquals("Please enter the correct number", exception.getMessage());
    }

    @Test
    void 주문추가_재고소진_비정상작동() {
        //given
        User user = new User("test_user", "test_pw");
        userRepository.save(user);
        Long userId = user.getId();

        Shop shop = new Shop();
        shopRepository.save(shop);

        ProductStock productStock = new ProductStock(1000, 0);
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
        Long orderListId = orderList.getId();

        //when
        OutOfStockException exception = assertThrows(OutOfStockException.class, () ->
                orderCreateService.create(userId, productId, 10, address, "카드")
        );

        //then
        assertEquals("This product is sold out", exception.getMessage());
    }
}