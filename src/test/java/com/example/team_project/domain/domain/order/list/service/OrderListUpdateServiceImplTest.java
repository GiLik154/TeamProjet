package com.example.team_project.domain.domain.order.list.service;

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
import com.example.team_project.domain.domain.shop.shop.domain.Shop;
import com.example.team_project.domain.domain.shop.shop.domain.ShopRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.OrderStatus;
import com.example.team_project.enums.PaymentType;
import com.example.team_project.enums.ProductCategoryStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderListUpdateServiceImplTest {

    private final UserRepository userRepository;
    private final UserAddressRepository userAddressRepository;
    private final PaymentRepository paymentRepository;
    private final ShopRepository shopRepository;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final OrderRepository orderRepository;
    private final OrderListRepository orderListRepository;
    private final OrderListUpdateServiceImpl orderListUpdateServiceImpl;

    @Autowired
    OrderListUpdateServiceImplTest(UserRepository userRepository,
                                   UserAddressRepository userAddressRepository,
                                   PaymentRepository paymentRepository,
                                   ShopRepository shopRepository,
                                   SellerRepository sellerRepository,
                                   ProductRepository productRepository,
                                   ProductCategoryRepository productCategoryRepository,
                                   OrderRepository orderRepository,
                                   OrderListRepository orderListRepository,
                                   OrderListUpdateServiceImpl orderListUpdateServiceImpl) {
        this.userRepository = userRepository;
        this.userAddressRepository = userAddressRepository;
        this.paymentRepository = paymentRepository;
        this.shopRepository = shopRepository;
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.orderRepository = orderRepository;
        this.orderListRepository = orderListRepository;
        this.orderListUpdateServiceImpl = orderListUpdateServiceImpl;
    }


    @Test
    void 주문리스트_수정_주소지변경_정상작동() {
        //given
        User user = new User("testId", "testPw", "testNane", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Payment payment = new Payment(user, PaymentType.CARD, "1111","");
        paymentRepository.save(payment);

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        UserAddress userAddress1 = new UserAddress(user, "오길식", "받는이2", "010-1111-1111", "전라북도 익산시", "익산아파트101호", "22222");
        userAddressRepository.save(userAddress);
        userAddressRepository.save(userAddress1);


        OrderList orderList = new OrderList(user, userAddress, payment, LocalDateTime.now());
        orderListRepository.save(orderList);
        Long orderListId = orderList.getId();

        //when
        orderListUpdateServiceImpl.update(userId, orderListId, userAddress1.getId());
        List<OrderList> orderListOptional = orderListRepository.findByUserId(userId);
        OrderList orderList1 = orderListOptional.get(0);

        //then
        assertEquals(orderList.getId(), orderList1.getId());
        assertEquals("오길식", orderList1.getUserAddress().getName());
        assertEquals("받는이2", orderList1.getUserAddress().getRecipientName());
        assertEquals("010-1111-1111", orderList1.getUserAddress().getPhone());
        assertEquals("전라북도 익산시", orderList1.getUserAddress().getStreetAddress());
        assertEquals("익산아파트101호", orderList1.getUserAddress().getDetailedAddress());
        assertEquals("22222", orderList1.getUserAddress().getZipCode());
    }

    @Test
    void 주문리스트_결제완료시_사용불가_정상작동() {
        //given
        User user = new User("testId", "testPw", "testNane", "testNumber");
        userRepository.save(user);

        Payment payment = new Payment(user, PaymentType.CARD, "1111","");
        paymentRepository.save(payment);

        Shop shop = new Shop();
        shopRepository.save(shop);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testDes", 99, 20, productCategory);
        Product product1 = new Product("testProduct1", seller, "testDes1", 99, 10, productCategory);
        productRepository.save(product);
        productRepository.save(product1);

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);

        OrderList orderList = new OrderList(user, userAddress, payment, LocalDateTime.now());
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
        orderListUpdateServiceImpl.paymentResult(orderListId);

        //then
        assertFalse(orderList.isStatus());
    }
}