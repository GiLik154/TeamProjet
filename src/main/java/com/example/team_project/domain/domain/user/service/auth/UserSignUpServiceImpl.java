package com.example.team_project.domain.domain.user.service.auth;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.UserGrade;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserSignUpServiceImpl implements UserSignUpService {
    private final UserRepository userRepository;
    private final UserAddressRepository userAddressRepository;

    public UserSignUpServiceImpl(UserRepository userRepository, UserAddressRepository userAddressRepository) {
        this.userRepository = userRepository;
        this.userAddressRepository = userAddressRepository;
    }

    @Override
    public User signUpUser(String userId, String password, String userName, String phoneNumber, String zipcode, String streetAddress, String detailedAddress) {
        User user = new User(userId, password, userName, phoneNumber, UserGrade.SILVER);
        userRepository.save(user);

        UserAddress userAddress = new UserAddress(zipcode, streetAddress, detailedAddress, user);
        userAddressRepository.save(userAddress);

        user.getUserAddresses().add(userAddress);

        return user;
    }
}
