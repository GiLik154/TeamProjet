package com.example.team_project.domain.domain.user.service.auth;

import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.domain.domain.user.dto.UserSignUpDto;
import com.example.team_project.enums.Role;
import com.example.team_project.enums.UserGrade;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class UserSignUpServiceImpl implements UserSignUpService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(UserSignUpDto userSignUpDto) {

        User user = new User(userSignUpDto.getUserId(),
                userSignUpDto.getPassword(),
                userSignUpDto.getUserName(),
                userSignUpDto.getEmail(),
                userSignUpDto.getPhoneNumber());

        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
    }

    /**
     * 회원가입 유효성 체크
     */
    @Override
    public Map<String, String> validateHandler(Errors errors) {
        Map<String, String> validateResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = "valid_" + error.getField();
            validateResult.put(validKeyName, error.getDefaultMessage());
        }

        if (errors.hasGlobalErrors()) {
            validateResult.put("error", errors.getGlobalErrors().get(0).getDefaultMessage());
        }

        return validateResult;
    }

    public boolean isUserIdDuplicated(String userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        return user.isPresent();
    }

    public boolean isEmailDuplicated(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    public boolean isPhoneNumberDuplicated(String phoneNumber) {
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
        return user.isPresent();
    }
}
