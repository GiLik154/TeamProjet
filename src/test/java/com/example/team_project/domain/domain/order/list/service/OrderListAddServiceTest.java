package com.example.team_project.domain.domain.order.list.service;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.UserGrade;
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
class OrderListAddServiceTest {

    private final UserRepository userRepository;
    private final OrderListAddService orderListAddService;
    private final OrderListRepository orderListRepository;
    private final UserAddressRepository userAddressRepository;

    @Autowired
    OrderListAddServiceTest(OrderListAddService orderListAddService, OrderListRepository orderListRepository, UserRepository userRepository, UserAddressRepository userAddressRepository) {
        this.orderListAddService = orderListAddService;
        this.orderListRepository = orderListRepository;
        this.userRepository = userRepository;
        this.userAddressRepository = userAddressRepository;
    }

    @Test
    void 주문리스트_생성_정상작동() {
        //given
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.VIP);
        userRepository.save(user);
        Long userId = user.getId();

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);

        //when
        orderListAddService.add(userId, userAddress, "카드");
        Optional<OrderList> orderListOptional = orderListRepository.findByUserId(userId);
        OrderList orderList = orderListOptional.get();

        //then
        assertNotNull(orderList.getId());
        assertEquals(userId, orderList.getUser().getId());
        assertEquals("서울특별시 강남구", orderList.getUserAddress().getStreetAddress());
        assertEquals("강남아파드101호", orderList.getUserAddress().getDetailedAddress());
        assertEquals("11111", orderList.getUserAddress().getZipCode());
        assertEquals("카드", orderList.getPaymentMethod());
    }

    @Test
    void 주문리스트_생성_유저아이디없음_비정상작동() {
        //given
        User user = new User("testId", "testPw", "testNane", "testNumber", UserGrade.VIP);
        userRepository.save(user);
        Long userId = user.getId();

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);

        //when
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                orderListAddService.add(userId + 1L, userAddress, "카드")
        );

        //then
        assertEquals("This user could not be found", exception.getMessage());
    }

}