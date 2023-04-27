package com.example.team_project.domain.domain.address.service.add;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
import com.example.team_project.domain.domain.address.service.add.dto.AddressAddServiceDto;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.UserGrade;
import com.example.team_project.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserAddressAddServiceImplTest {
    private final AddressAddService addressAddService;
    private final UserAddressRepository userAddressRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserAddressAddServiceImplTest(AddressAddService addressAddService, UserAddressRepository userAddressRepository, UserRepository userRepository) {
        this.addressAddService = addressAddService;
        this.userAddressRepository = userAddressRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 주소_추가_정상작동() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        AddressAddServiceDto dto = new AddressAddServiceDto("testName",
                "testRecipientName",
                "testPhone",
                "testStreetAddress",
                "testDetailAddress",
                "testZipCode");

        addressAddService.add(userId, dto);

        UserAddress userAddress = userAddressRepository.findByUserId(userId).get(0);

        assertNotNull(userAddress.getId());
        assertEquals(user, userAddress.getUser());
        assertEquals("testName", userAddress.getName());
        assertEquals("testRecipientName", userAddress.getRecipientName());
        assertEquals("testPhone", userAddress.getPhone());
        assertEquals("testStreetAddress", userAddress.getStreetAddress());
        assertEquals("testDetailAddress", userAddress.getDetailedAddress());
        assertEquals("testZipCode", userAddress.getZipCode());
    }

    @Test
    void 주소_추가_유저_고유번호_다름() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");;
        userRepository.save(user);
        Long userId = user.getId();

        AddressAddServiceDto dto = new AddressAddServiceDto("testName",
                "testRecipientName",
                "testPhone",
                "testStreetAddress",
                "testDetailAddress",
                "testZipCode");

        UserNotFoundException e = assertThrows(UserNotFoundException.class, () ->
                addressAddService.add(userId + 1L, dto));

        assertEquals("This user could not be found", e.getMessage());
    }
}