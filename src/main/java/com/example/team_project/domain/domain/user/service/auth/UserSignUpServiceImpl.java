package com.example.team_project.domain.domain.user.service.auth;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
import com.example.team_project.domain.domain.address.service.add.AddressAddServiceImpl;
import com.example.team_project.domain.domain.address.service.add.dto.AddressAddServiceDto;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.UserGrade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserSignUpServiceImpl implements UserSignUpService {
    private final UserRepository userRepository;
    private final UserAddressRepository userAddressRepository;

    public UserSignUpServiceImpl(UserRepository userRepository, UserAddressRepository userAddressRepository) {
        this.userRepository = userRepository;
        this.userAddressRepository = userAddressRepository;
    }

    @Override
    public void signUpUser(String userId, String password, String userName, String email, String phoneNumber) {
        User user = new User(userId, password, userName, email, phoneNumber);
        userRepository.save(user);
    }
}
