package com.example.team_project.domain.domain.address.service.delete;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.UserGrade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AddressDeleteServiceImplTest {
    private final AddressDeleteService addressDeleteService;
    private final UserAddressRepository userAddressRepository;
    private final UserRepository userRepository;

    @Autowired
    public AddressDeleteServiceImplTest(AddressDeleteService addressDeleteService, UserAddressRepository userAddressRepository, UserRepository userRepository) {
        this.addressDeleteService = addressDeleteService;
        this.userAddressRepository = userAddressRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 주소_삭제_정상작동() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        UserAddress userAddress = new UserAddress(user,
                "testName",
                "recipientName",
                "testPhone",
                "testStreetAddress",
                "testDetailAddress",
                "testZipCode");

        userAddressRepository.save(userAddress);
        Long addressId = userAddress.getId();

        addressDeleteService.delete(userId, addressId);

        Optional<UserAddress> optional = userAddressRepository.findById(addressId);

        assertFalse(optional.isPresent());
    }

    @Test
    void 주소_삭제_유저_고유번호_다름() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        UserAddress userAddress = new UserAddress(user,
                "testName",
                "recipientName",
                "testPhone",
                "testStreetAddress",
                "testDetailAddress",
                "testZipCode");

        userAddressRepository.save(userAddress);
        Long addressId = userAddress.getId();

        addressDeleteService.delete(userId + 1L, addressId);

        Optional<UserAddress> optional = userAddressRepository.findById(addressId);

        assertTrue(optional.isPresent());
    }

    @Test
    void 주소_삭제_주소_고유번호_다름() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        UserAddress userAddress = new UserAddress(user,
                "testName",
                "recipientName",
                "testPhone",
                "testStreetAddress",
                "testDetailAddress",
                "testZipCode");

        userAddressRepository.save(userAddress);
        Long addressId = userAddress.getId();

        addressDeleteService.delete(userId, addressId + 1L);

        Optional<UserAddress> optional = userAddressRepository.findById(addressId);

        assertTrue(optional.isPresent());
    }
}