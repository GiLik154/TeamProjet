package com.example.team_project.domain.domain.order.list.service;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.payment.domain.Payment;
import com.example.team_project.domain.domain.payment.domain.PaymentRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.PaymentType;
import com.example.team_project.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderListAddServiceImplTest {

    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final OrderListAddServiceImpl orderListAddServiceImpl;
    private final OrderListRepository orderListRepository;
    private final UserAddressRepository userAddressRepository;

    @Autowired
    OrderListAddServiceImplTest(OrderListAddServiceImpl orderListAddServiceImpl, OrderListRepository orderListRepository, UserRepository userRepository, PaymentRepository paymentRepository, UserAddressRepository userAddressRepository) {
        this.orderListAddServiceImpl = orderListAddServiceImpl;
        this.orderListRepository = orderListRepository;
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
        this.userAddressRepository = userAddressRepository;
    }

    @Test
    void 주문리스트_생성_정상작동() {
        //given
        User user = new User("testId", "testPw", "testNane", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Payment payment = new Payment(user, PaymentType.CARD, "1111","");
        paymentRepository.save(payment);
        Long paymentId = payment.getId();

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);
        Long userAddressId = userAddress.getId();

        //when
        orderListAddServiceImpl.add(userId, userAddressId, paymentId);
        List<OrderList> orderListOptional = orderListRepository.findByUserId(userId);
        OrderList orderList = orderListOptional.get(0);

        //then
        assertNotNull(orderList.getId());
        assertEquals(userId, orderList.getUser().getId());
        assertEquals("서울특별시 강남구", orderList.getUserAddress().getStreetAddress());
        assertEquals("강남아파드101호", orderList.getUserAddress().getDetailedAddress());
        assertEquals("11111", orderList.getUserAddress().getZipCode());
        assertEquals("CARD", orderList.getPayment().getPaymentType().toString());
        System.out.println("==============================================================" + orderList.getOrderDate());
    }

    @Test
    void 주문리스트_생성_유저아이디없음_비정상작동() {
        //given
        User user = new User("testId", "testPw", "testNane", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Payment payment = new Payment(user, PaymentType.CARD, "1111","");
        paymentRepository.save(payment);
        Long paymentId = payment.getId();


        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);
        Long userAddressId = userAddress.getId();

        //when
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                orderListAddServiceImpl.add(userId + 1L, userAddressId, paymentId)
        );

        //then
        assertEquals("This user could not be found", exception.getMessage());
    }

}