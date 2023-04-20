package com.example.team_project.domain.domain.order.item.service;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.item.domain.OrderToProduct;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.payment.domain.Payment;
import com.example.team_project.domain.domain.payment.domain.PaymentRepository;
import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.product.category.domain.ProductCategoryRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.PaymentType;
import com.example.team_project.enums.ProductCategoryStatus;
import com.example.team_project.exception.InvalidQuantityException;
import com.example.team_project.exception.OutOfStockException;
import com.example.team_project.exception.ProductNotFoundException;
import com.example.team_project.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderCreateServiceImplTest {

    private final UserRepository userRepository;
    private final UserAddressRepository userAddressRepository;
    private final PaymentRepository paymentRepository;
    private final SellerRepository sellerRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderCreateService orderCreateService;
    private final OrderListRepository orderListRepository;
    private final ProductCategoryRepository productCategoryRepository;


    @Autowired
    OrderCreateServiceImplTest(
            SellerRepository sellerRepository,
            UserRepository userRepository,
            UserAddressRepository userAddressRepository,
            PaymentRepository paymentRepository,
            OrderRepository orderRepository,
            ProductRepository productRepository,
            OrderCreateService orderCreateService, OrderListRepository orderListRepository,
            ProductCategoryRepository productCategoryRepository) {
        this.sellerRepository = sellerRepository;
        this.orderCreateService = orderCreateService;
        this.userRepository = userRepository;
        this.userAddressRepository = userAddressRepository;
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderListRepository = orderListRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Test
    void 주문추가_정상작동() {
        //given
        User user = new User("testId", "testPw", "testNane", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);
        Long userAddressId = userAddress.getId();

        Payment payment = new Payment(user, PaymentType.CARD, "1111", "2222");
        paymentRepository.save(payment);
        Long paymentId = payment.getId();


        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testDes", 99, 20, productCategory);
        productRepository.save(product);
        Long productId = product.getId();

        //when
        Order order = orderCreateService.create(userId, productId, 10, userAddressId, paymentId);
//        Optional<Order> orderOptional = orderRepository.findByUserId(userId);
//        Order order = orderOptional.get();

        //then
        assertNotNull(order.getId());
        assertEquals(userId, order.getUser().getId());
        assertEquals(productId, order.getOrderToProduct().getProduct().getId());
        assertEquals("CARD", order.getOrderList().getPayment().getPaymentType().toString());
        assertEquals("1111", order.getOrderList().getPayment().getCardNumber());
        assertEquals("testProduct", order.getOrderToProduct().getProduct().getName());
        assertEquals("testDes", order.getOrderToProduct().getProduct().getDescription());
    }

    @Test
    void 주문추가_유효하지_않은_사용자_비정삭작동() {
        //given
        User user = new User("testId", "testPw", "testNane", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);
        Long userAddressId = userAddress.getId();

        Payment payment = new Payment(user, PaymentType.CARD, "1111", "2222");
        paymentRepository.save(payment);
        Long paymentId = payment.getId();

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testDes", 99, 20, productCategory);
        productRepository.save(product);
        Long productId = product.getId();

        OrderList orderList = new OrderList(user, userAddress, payment, LocalDateTime.now());
        orderListRepository.save(orderList);

        //when
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                orderCreateService.create(userId + 1L, productId, 10, userAddressId, paymentId)
        );

        //then
        assertEquals("This user could not be found", exception.getMessage());
    }

    @Test
    void 주문추가_가격확인_정상작동() {
        //given
        User user = new User("testId", "testPw", "testNane", "testNumber");
        userRepository.save(user);

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);

        Payment payment = new Payment(user, PaymentType.CARD, "1111", "2222");
        paymentRepository.save(payment);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testDes", 99, 5000, productCategory);
        productRepository.save(product);
        Product product1 = new Product("testProduct", seller, "testDes", 99, 1000, productCategory);

        OrderList orderList = new OrderList(user, userAddress, payment, LocalDateTime.now());
        orderListRepository.save(orderList);

        OrderToProduct orderToProduct = new OrderToProduct(product, 10);
        OrderToProduct orderToProduct1 = new OrderToProduct(product1, 10);

        Order order = new Order(user, orderList, orderToProduct);
        Order order1 = new Order(user, orderList, orderToProduct1);
        orderRepository.save(order);
        orderRepository.save(order1);

        //then
        assertEquals(50000, orderToProduct.getTotalPrice());
        assertEquals(10000, orderToProduct1.getTotalPrice());

    }

    @Test
    void 주문추가_유효하지_않는_상품_비정상작동() {
        //given
        User user = new User("testId", "testPw", "testNane", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);
        Long userAddressId = userAddress.getId();

        Payment payment = new Payment(user, PaymentType.CARD, "1111", "2222");
        paymentRepository.save(payment);
        Long paymentId = payment.getId();

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testDes", 99, 20, productCategory);
        productRepository.save(product);
        Long productId = product.getId();

        OrderList orderList = new OrderList(user, userAddress, payment, LocalDateTime.now());
        orderListRepository.save(orderList);

        OrderToProduct orderToProduct = new OrderToProduct(product, 10);

        Order order = new Order(user, orderList, orderToProduct);
        orderRepository.save(order);

        //when
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () ->
                orderCreateService.create(userId, productId + 1L, 10, userAddressId, paymentId)
        );

        //then
        assertEquals("This product does not exist", exception.getMessage());
    }

    @Test
    void 주문추가_주문개수0이하_비정상작동() {
        //given
        User user = new User("testId", "testPw", "testNane", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);
        Long userAddressId = userAddress.getId();

        Payment payment = new Payment(user, PaymentType.CARD, "1111", "2222");
        paymentRepository.save(payment);
        Long paymentId = payment.getId();

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testDes", 99, 20, productCategory);
        productRepository.save(product);
        Long productId = product.getId();

        OrderList orderList = new OrderList(user, userAddress, payment, LocalDateTime.now());
        orderListRepository.save(orderList);

        //when
        InvalidQuantityException exception = assertThrows(InvalidQuantityException.class, () ->
                orderCreateService.create(userId, productId, 0, userAddressId, paymentId)
        );

        //then
        assertEquals("Please enter the correct number", exception.getMessage());
    }

    @Test
    void 주문추가_재고소진_비정상작동() {
        //given
        User user = new User("testId", "testPw", "testNane", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);
        Long userAddressId = userAddress.getId();

        Payment payment = new Payment(user, PaymentType.CARD, "1111", "2222");
        paymentRepository.save(payment);
        Long paymentId = payment.getId();

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testDes", 0, 20, productCategory);
        productRepository.save(product);
        Long productId = product.getId();

        OrderList orderList = new OrderList(user, userAddress, payment, LocalDateTime.now());
        orderListRepository.save(orderList);

        //when
        OutOfStockException exception = assertThrows(OutOfStockException.class, () ->
                orderCreateService.create(userId, productId, 10, userAddressId, paymentId)
        );

        //then
        assertEquals("This product is sold out", exception.getMessage());
    }
}