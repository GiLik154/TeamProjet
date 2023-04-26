//package com.example.team_project.controller.core.order.create;
//
//import com.example.team_project.controller.advice.order.ControllersAdvice;
//import com.example.team_project.controller.interceptor.UserLoginInterceptor;
//import com.example.team_project.domain.domain.address.domain.UserAddress;
//import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
//import com.example.team_project.domain.domain.order.item.domain.Order;
//import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
//import com.example.team_project.domain.domain.order.item.domain.OrderToProduct;
//import com.example.team_project.domain.domain.order.item.service.OrderCreateService;
//import com.example.team_project.domain.domain.order.list.domain.OrderList;
//import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
//import com.example.team_project.domain.domain.payment.domain.Payment;
//import com.example.team_project.domain.domain.payment.domain.PaymentRepository;
//import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
//import com.example.team_project.domain.domain.product.category.domain.ProductCategoryRepository;
//import com.example.team_project.domain.domain.product.product.domain.Product;
//import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
//import com.example.team_project.domain.domain.shop.seller.domain.Seller;
//import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
//import com.example.team_project.domain.domain.user.domain.User;
//import com.example.team_project.domain.domain.user.domain.UserRepository;
//import com.example.team_project.enums.PaymentType;
//import com.example.team_project.enums.ProductCategoryStatus;
//import com.example.team_project.enums.UserGrade;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockHttpSession;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@Transactional
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class OrderCreateGetControllerTest {
//
//    private final OrderCreateController orderCreateController;
//    private final UserRepository userRepository;
//    private final UserAddressRepository userAddressRepository;
//    private final PaymentRepository paymentRepository;
//    private final SellerRepository sellerRepository;
//    private final ProductRepository productRepository;
//    private final OrderListRepository orderListRepository;
//    private final ProductCategoryRepository productCategoryRepository;
//    private final UserLoginInterceptor userLoginInterceptor;
//    private final ControllersAdvice controllersAdvice;
//    private MockMvc mockMvc;
//
//    @Autowired
//    OrderCreateGetControllerTest(OrderCreateController orderCreateController, UserRepository userRepository, UserAddressRepository userAddressRepository, PaymentRepository paymentRepository, SellerRepository sellerRepository, ProductRepository productRepository, OrderListRepository orderListRepository, ProductCategoryRepository productCategoryRepository, UserLoginInterceptor userLoginInterceptor, ControllersAdvice controllersAdvice) {
//        this.orderCreateController = orderCreateController;
//        this.userRepository = userRepository;
//        this.userAddressRepository = userAddressRepository;
//        this.paymentRepository = paymentRepository;
//        this.sellerRepository = sellerRepository;
//        this.productRepository = productRepository;
//        this.orderListRepository = orderListRepository;
//        this.productCategoryRepository = productCategoryRepository;
//        this.userLoginInterceptor = userLoginInterceptor;
//        this.controllersAdvice = controllersAdvice;
//    }
//
//    @BeforeEach
//    public void setup() {
//        mockMvc = MockMvcBuilders.standaloneSetup(orderCreateController)
//                .setControllerAdvice(controllersAdvice)
//                .addInterceptors(userLoginInterceptor)
//                .build();
//    }
//
//    @Test
//    void 주문_추가_정상작동() throws Exception {
//        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.GOLD);
//        userRepository.save(user);
//        Long userId = user.getId();
//
//        MockHttpSession mockHttpSession = new MockHttpSession();
//        mockHttpSession.setAttribute("userId", userId);
//
//        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
//        userAddressRepository.save(userAddress);
//
//        Payment payment = new Payment(user, PaymentType.CARD, "1111", "2222");
//        paymentRepository.save(payment);
//
//        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
//        productCategoryRepository.save(productCategory);
//
//        Seller seller = new Seller("testSellerName", "testSellerPw");
//        sellerRepository.save(seller);
//
//        Product product = new Product("testProduct", seller, "testDes", 99, 20, productCategory);
//        productRepository.save(product);
//        Long productId = product.getId();
//
//        OrderList orderList = new OrderList(user, userAddress, payment, LocalDateTime.now());
//        orderListRepository.save(orderList);
//
//        List<UserAddress> userAddressList = userAddressRepository.findByUserId(userId);
//        List<Payment> paymentList = paymentRepository.findListByUserId(userId);
//
//        MockHttpServletRequestBuilder builder = get("/order/create/" + productId)
//                .param("salesCount", String.valueOf(5))
//                .session(mockHttpSession);
//
//        mockMvc.perform(builder)
//                .andExpect(status().isOk())
//                .andExpect(forwardedUrl("thymeleaf/order/order_create"))
//                .andExpect(model().attribute("user_address_list", userAddressList))
//                .andExpect(model().attribute("payment_list", paymentList))
//                .andExpect(model().attribute("product", product))
//                .andExpect(model().attribute("quantity", 5));
//    }
//
//    @Test
//    void 주문_추가_상품고유번호다름() throws Exception {
//        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.GOLD);
//        userRepository.save(user);
//        Long userId = user.getId();
//
//        MockHttpSession mockHttpSession = new MockHttpSession();
//        mockHttpSession.setAttribute("userId", userId);
//
//        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
//        userAddressRepository.save(userAddress);
//
//        Payment payment = new Payment(user, PaymentType.CARD, "1111", "2222");
//        paymentRepository.save(payment);
//
//        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
//        productCategoryRepository.save(productCategory);
//
//        Seller seller = new Seller("testSellerName", "testSellerPw");
//        sellerRepository.save(seller);
//
//        Product product = new Product("testProduct", seller, "testDes", 1, 20, productCategory);
//        productRepository.save(product);
//        Long productId = product.getId();
//
//        OrderList orderList = new OrderList(user, userAddress, payment, LocalDateTime.now());
//        orderListRepository.save(orderList);
//
//        MockHttpServletRequestBuilder builder = post("/order/create")
//                .param("productId", String.valueOf(productId))
//                .param("quantity", String.valueOf(20))
//                .param("userAddressId", String.valueOf(userAddress.getId()))
//                .param("paymentId", String.valueOf(payment.getId()))
//                .session(mockHttpSession);
//
//        mockMvc.perform(builder)
//                .andExpect(status().isOk())
//                .andExpect(forwardedUrl("thymeleaf/error/error-page"));
//
//    }
//}