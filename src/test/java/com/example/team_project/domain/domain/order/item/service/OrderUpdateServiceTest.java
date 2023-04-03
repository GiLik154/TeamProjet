package com.example.team_project.domain.domain.order.item.service;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.item.domain.OrderToProduct;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.product.category.domain.ProductCategoryRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.shop.shop.domain.Shop;
import com.example.team_project.domain.domain.shop.shop.domain.ShopRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.OrderStatus;
import com.example.team_project.enums.ProductCategoryStatus;
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
    private final UserAddressRepository userAddressRepository;
    private final ShopRepository shopRepository;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final OrderListRepository orderListRepository;
    private final OrderRepository orderRepository;
    private final OrderUpdateService orderUpdateService;

    @Autowired
    public OrderUpdateServiceTest(UserRepository userRepository,
                                  UserAddressRepository userAddressRepository,
                                  ShopRepository shopRepository,
                                  SellerRepository sellerRepository,
                                  ProductRepository productRepository,
                                  ProductCategoryRepository productCategoryRepository,
                                  OrderListRepository orderListRepository,
                                  OrderRepository orderRepository,
                                  OrderUpdateService orderUpdateService) {
        this.userRepository = userRepository;
        this.userAddressRepository = userAddressRepository;
        this.shopRepository = shopRepository;
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
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

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", "testDes", 5, 5000, productCategory);
        Product product1 = new Product("update_name", seller, "update_image", "update_description", 10, 10000, productCategory);
        productRepository.save(product);
        productRepository.save(product1);
        Long productId1 = product1.getId();

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);

        OrderList orderList = new OrderList(user, userAddress, "카드");
        orderListRepository.save(orderList);

        OrderToProduct orderToProduct = new OrderToProduct(product, 1);

        Order order = new Order(user, orderList, orderToProduct);
        orderRepository.save(order);
        Long orderId = order.getId();

        //when
        orderUpdateService.update(productId1, orderId, 5);

        //then
        assertNotNull(orderId);
        assertEquals("update_name", order.getOrderToProduct().getProduct().getName());
        assertEquals("update_image", order.getOrderToProduct().getProduct().getImage());
        assertEquals("update_description", order.getOrderToProduct().getProduct().getDescription());
    }

    @Test
    void 주문상품_수정_주문개수0이하_비정상작동() {
        //given
        User user = new User("test_user", "test_pw");
        userRepository.save(user);

        Shop shop = new Shop();
        shopRepository.save(shop);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", "testDes", 20, 5000, productCategory);
        Product product1 = new Product("testProduct1", seller, "testImg1", "testDes1", 10, 10000, productCategory);
        productRepository.save(product);
        productRepository.save(product1);
        Long productId1 = product1.getId();

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);


        OrderList orderList = new OrderList(user, userAddress, "카드");
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

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", "testDes", 10, 5000, productCategory);
        Product product1 = new Product("testProduct1", seller, "testImg1", "testDes1", 10, 10000, productCategory);
        productRepository.save(product);
        productRepository.save(product1);
        Long productId1 = product1.getId();

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);

        OrderList orderList = new OrderList(user, userAddress, "카드");
        orderListRepository.save(orderList);

        OrderToProduct orderToProduct = new OrderToProduct(product, 5);

        Order order = new Order(user, orderList, orderToProduct);
        orderRepository.save(order);
        Long orderId = order.getId();

        //when
        OutOfStockException exception = assertThrows(OutOfStockException.class, () ->
                orderUpdateService.update(productId1, orderId, 15)
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

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", "testDes", 20, 5000, productCategory);
        productRepository.save(product);
        Long productId = product.getId();

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);

        OrderList orderList = new OrderList(user, userAddress, "카드");
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

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", "testDes", 20, 5000, productCategory);
        productRepository.save(product);
        Long productId = product.getId();

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);

        OrderList orderList = new OrderList(user, userAddress, "카드");
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