package com.example.team_project.domain.domain.order.item.service;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.item.domain.OrderToProduct;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.order.list.service.OrderListCancelServiceImpl;
import com.example.team_project.domain.domain.payment.domain.Payment;
import com.example.team_project.domain.domain.payment.domain.PaymentRepository;
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
import com.example.team_project.enums.*;
import com.example.team_project.exception.CannotCancelOrderException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderCancelServiceImplTest {

    private final UserRepository userRepository;
    private final UserAddressRepository userAddressRepository;
    private final PaymentRepository paymentRepository;
    private final ShopRepository shopRepository;
    private final SellerRepository sellerRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderListRepository orderListRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final OrderCancelService orderCancelService;
    private final OrderListCancelServiceImpl orderListCancelServiceImpl;

    @Autowired
    OrderCancelServiceImplTest(UserRepository userRepository,
                               UserAddressRepository userAddressRepository,
                               PaymentRepository paymentRepository,
                               ShopRepository shopRepository,
                               SellerRepository sellerRepository,
                               OrderRepository orderRepository,
                               ProductRepository productRepository,
                               OrderListRepository orderListRepository,
                               ProductCategoryRepository productCategoryRepository,
                               OrderCancelService orderCancelService,
                               OrderListCancelServiceImpl orderListCancelServiceImpl) {
        this.userRepository = userRepository;
        this.userAddressRepository = userAddressRepository;
        this.paymentRepository = paymentRepository;
        this.shopRepository = shopRepository;
        this.sellerRepository = sellerRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderListRepository = orderListRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.orderCancelService = orderCancelService;
        this.orderListCancelServiceImpl = orderListCancelServiceImpl;
    }

    @Test
    void 주문상품_취소_정상작동() {
        //given
        User user = new User("testId", "testPassword", "testName", "testEmail", "testPhone");
        userRepository.save(user);
        Long userId = user.getId();

        Payment payment = new Payment(user, "paymentName", PaymentType.CARD, "1111");
        paymentRepository.save(payment);

        Shop shop = new Shop();
        shopRepository.save(shop);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testDes", 99, 20, productCategory);
        productRepository.save(product);

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);

        OrderList orderList = new OrderList(user, userAddress, payment, LocalDate.now());
        orderListRepository.save(orderList);
        Long orderListId = orderList.getId();

        OrderToProduct orderToProduct = new OrderToProduct(product, 10);
        Order order = new Order(user, orderList, orderToProduct);
        orderRepository.save(order);
        Long orderId = order.getId();
        Long orderToProductId = orderToProduct.getId();

        //when
        orderCancelService.cancel(userId, orderToProductId, orderId, orderListId);

        //then
        assertEquals("CANCELED", order.getOrderToProduct().getStatus().toString());
    }

    @Test
    void 주문상품_취소불가_정상작동() {
        //given
        User user = new User("testId", "testPassword", "testName", "testEmail", "testPhone");
        userRepository.save(user);

        Payment payment = new Payment(user, "paymentName", PaymentType.CARD, "1111");
        paymentRepository.save(payment);

        Shop shop = new Shop();
        shopRepository.save(shop);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testDes", 99, 20, productCategory);
        productRepository.save(product);

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);

        OrderList orderList = new OrderList(user, userAddress, payment, LocalDate.now());
        orderListRepository.save(orderList);
        Long orderListId = orderList.getId();

        OrderToProduct orderToProduct = new OrderToProduct(product, 10);
        Order order = new Order(user, orderList, orderToProduct);
        orderRepository.save(order);

        orderToProduct.updateStatus(OrderStatus.SHIPPED);

        //when
        CannotCancelOrderException exception = assertThrows(CannotCancelOrderException.class, () ->
                orderListCancelServiceImpl.cancel(orderListId)
        );

        //then
        assertEquals("This order cannot be canceled", exception.getMessage());
    }

    @Test
    void 주문상품리스트_취소_정상작동() {
        //given
        User user = new User("testId", "testPassword", "testName", "testEmail", "testPhone");
        userRepository.save(user);

        Payment payment = new Payment(user, "paymentName", PaymentType.CARD, "1111");
        paymentRepository.save(payment);

        Shop shop = new Shop();
        shopRepository.save(shop);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testDes", 99, 20, productCategory);
        Product product1 = new Product("testProduct", seller, "testDes", 99, 40, productCategory);
        productRepository.save(product);
        productRepository.save(product1);

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);

        OrderList orderList = new OrderList(user, userAddress, payment, LocalDate.now());
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
        orderListCancelServiceImpl.cancel(orderListId);

        //then
        assertEquals("CANCELED", order.getOrderToProduct().getStatus().toString());
        assertEquals("CANCELED", order1.getOrderToProduct().getStatus().toString());
    }


}