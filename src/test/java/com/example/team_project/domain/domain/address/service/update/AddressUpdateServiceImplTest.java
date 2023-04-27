package com.example.team_project.domain.domain.address.service.update;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
import com.example.team_project.domain.domain.address.service.update.dto.AddressUpdateServiceDto;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.UserGrade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AddressUpdateServiceImplTest {
    private final AddressUpdateService addressUpdateService;
    private final UserAddressRepository userAddressRepository;
    private final UserRepository userRepository;

    @Autowired
    public AddressUpdateServiceImplTest(AddressUpdateService addressUpdateService, UserAddressRepository userAddressRepository, UserRepository userRepository) {
        this.addressUpdateService = addressUpdateService;
        this.userAddressRepository = userAddressRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 주소_업데이트_정상작동() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        UserAddress userAddress = new UserAddress(user,
                "testName",
                "testRecipientName",
                "testPhone",
                "testStreetAddress",
                "testDetailAddress",
                "testZipCode");

        userAddressRepository.save(userAddress);
        Long addressId = userAddress.getId();

        AddressUpdateServiceDto dto = new AddressUpdateServiceDto("updateName",
                "updateRecipientName",
                "updatePhone",
                "updateStreetAddress",
                "updateDetailAddress",
                "updateZipCode");

        addressUpdateService.update(userId, addressId, dto);

        UserAddress updateUserAddress = userAddressRepository.findById(addressId).get();

        assertNotNull(userAddress.getId());
        assertEquals("updateName", updateUserAddress.getName());
        assertEquals("updateRecipientName", updateUserAddress.getRecipientName());
        assertEquals("updatePhone", updateUserAddress.getPhone());
        assertEquals("updateStreetAddress", updateUserAddress.getStreetAddress());
        assertEquals("updateDetailAddress", updateUserAddress.getDetailedAddress());
        assertEquals("updateZipCode", updateUserAddress.getZipCode());
    }

    @Test
    void 주소_업데이트_유저_고유번호다름() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        UserAddress userAddress = new UserAddress(user,
                "testName",
                "testRecipientName",
                "testPhone",
                "testStreetAddress",
                "testDetailAddress",
                "testZipCode");

        userAddressRepository.save(userAddress);
        Long addressId = userAddress.getId();

        AddressUpdateServiceDto dto = new AddressUpdateServiceDto("updateName",
                "updateRecipientName",
                "updatePhone",
                "updateStreetAddress",
                "updateDetailAddress",
                "updateZipCode");

        addressUpdateService.update(userId + 1L, addressId, dto);

        UserAddress updateUserAddress = userAddressRepository.findById(addressId).get();

        assertEquals("testName", updateUserAddress.getName());
        assertEquals("testRecipientName", updateUserAddress.getRecipientName());
        assertEquals("testPhone", updateUserAddress.getPhone());
        assertEquals("testStreetAddress", updateUserAddress.getStreetAddress());
        assertEquals("testDetailAddress", updateUserAddress.getDetailedAddress());
        assertEquals("testZipCode", updateUserAddress.getZipCode());
    }

    @Test
    void 주소_업데이트_주소_고유번호_다름() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        UserAddress userAddress = new UserAddress(user,
                "testName",
                "testRecipientName",
                "testPhone",
                "testStreetAddress",
                "testDetailAddress",
                "testZipCode");

        userAddressRepository.save(userAddress);
        Long addressId = userAddress.getId();

        AddressUpdateServiceDto dto = new AddressUpdateServiceDto("updateName",
                "updateRecipientName",
                "updatePhone",
                "updateStreetAddress",
                "updateDetailAddress",
                "updateZipCode");

        addressUpdateService.update(userId, addressId + 1L, dto);

        UserAddress updateUserAddress = userAddressRepository.findById(addressId).get();

        assertEquals("testName", updateUserAddress.getName());
        assertEquals("testRecipientName", updateUserAddress.getRecipientName());
        assertEquals("testPhone", updateUserAddress.getPhone());
        assertEquals("testStreetAddress", updateUserAddress.getStreetAddress());
        assertEquals("testDetailAddress", updateUserAddress.getDetailedAddress());
        assertEquals("testZipCode", updateUserAddress.getZipCode());
    }
}