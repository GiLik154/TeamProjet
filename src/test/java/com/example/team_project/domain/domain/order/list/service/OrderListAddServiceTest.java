package com.example.team_project.domain.domain.order.list.service;

import com.example.team_project.domain.domain.order.list.Address;
import com.example.team_project.domain.domain.order.list.AddressRepository;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
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
    private final AddressRepository addressRepository;
    private final OrderListAddService orderListAddService;
    private final OrderListRepository orderListRepository;

    @Autowired
    OrderListAddServiceTest(OrderListAddService orderListAddService, OrderListRepository orderListRepository, UserRepository userRepository, AddressRepository addressRepository) {
        this.orderListAddService = orderListAddService;
        this.orderListRepository = orderListRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @Test
    void 주문리스트_생성_정상작동() {
        //given
        User user = new User("test_name", "test_pw");
        userRepository.save(user);
        Long userId = user.getId();

        Address address = new Address("서울", "강남구");
        addressRepository.save(address);

        //when
        orderListAddService.add(userId, address, "카드");
        Optional<OrderList> orderListOptional = orderListRepository.findByUserId(userId);
        OrderList orderList = orderListOptional.get();

        //then
        assertNotNull(orderList.getId());
        assertEquals(userId, orderList.getUser().getId());
        assertEquals("서울", orderList.getAddress().getCity());
        assertEquals("강남구", orderList.getAddress().getDistrict());
        assertEquals("카드", orderList.getPaymentMethod());
    }

    @Test
    void 주문리스트_생성_유저아이디없음_비정상작동() {
        //given
        User user = new User("test_name", "test_pw");
        userRepository.save(user);
        Long userId = user.getId();

        Address address = new Address("서울", "강남구");
        addressRepository.save(address);

        //when
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                orderListAddService.add(userId + 1L, address, "카드")
        );

        //then
        assertEquals("This user could not be found", exception.getMessage());
    }

}