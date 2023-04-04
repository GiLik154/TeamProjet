package com.example.team_project.domain.domain.order.item.service;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.item.domain.OrderToProduct;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.order.list.service.OrderListCancelService;
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
import com.example.team_project.enums.UserGrade;
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

    private final UserRepository userRepository;
    private final UserAddressRepository userAddressRepository;
    private final ShopRepository shopRepository;
    private final SellerRepository sellerRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderListRepository orderListRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final OrderListCancelService orderListCancelService;
    private final OrderCancelService orderCancelService;

    @Autowired
    OrderCancelServiceTest(UserRepository userRepository,
                           UserAddressRepository userAddressRepository,
                           ShopRepository shopRepository,
                           SellerRepository sellerRepository,
                           OrderRepository orderRepository,
                           ProductRepository productRepository,
                           OrderListRepository orderListRepository,
                           ProductCategoryRepository productCategoryRepository,
                           OrderListCancelService orderListCancelService,
                           OrderCancelService orderCancelService) {
        this.userRepository = userRepository;
        this.userAddressRepository = userAddressRepository;
        this.shopRepository = shopRepository;
        this.sellerRepository = sellerRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderListRepository = orderListRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.orderListCancelService = orderListCancelService;
        this.orderCancelService = orderCancelService;
    }

    @Test
    void 주문상품_취소_정상작동() {
        //given
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.SILVER);
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
        Long orderToProductId = orderToProduct.getId();

        //when
        orderCancelService.cancel(orderToProductId, orderId);

        //then
        assertEquals("CANCELED", order.getOrderToProduct().getStatus().toString());
    }

    @Test
    void 주문상품_취소불가_정상작동() {
        //given
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.SILVER);
        userRepository.save(user);

        Shop shop = new Shop();
        shopRepository.save(shop);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", "testDes", 20, 5000, productCategory);
        productRepository.save(product);

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);

        OrderList orderList = new OrderList(user, userAddress, "카드");
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
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.SILVER);
        userRepository.save(user);

        Shop shop = new Shop();
        shopRepository.save(shop);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", "testDes", 20, 5000, productCategory);
        Product product1 = new Product("testProduct1", seller, "testImg1", "testDes1", 50, 10000, productCategory);
        productRepository.save(product);
        productRepository.save(product1);

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);

        OrderList orderList = new OrderList(user, userAddress, "카드");
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