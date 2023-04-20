package com.example.team_project.domain.domain.user.service.auth;

import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.domain.domain.user.service.auth.dto.UserSignUpDTO;
import com.example.team_project.enums.Role;
import com.example.team_project.exception.EmailAlreadyExistsException;
import com.example.team_project.exception.UserIdAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserSignUpServiceImpl implements UserSignUpService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public UserSignUpServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signUp(UserSignUpDTO userSignUpDTO) throws UserIdAlreadyExistsException, EmailAlreadyExistsException {

        if(userRepository.existsByUserId(userSignUpDTO.getUserId())) {
            throw new UserIdAlreadyExistsException();
        }

        if (userRepository.existsByEmail(userSignUpDTO.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        User user = new User(userSignUpDTO.getUserId(), userSignUpDTO.getPassword(),
                userSignUpDTO.getUserName(), userSignUpDTO.getEmail(), userSignUpDTO.getPhoneNumber());
        user.changePassword(passwordEncoder.encode(userSignUpDTO.getPassword()));
        user.addRole(Role.USER);
        userRepository.save(user);
    }
}
