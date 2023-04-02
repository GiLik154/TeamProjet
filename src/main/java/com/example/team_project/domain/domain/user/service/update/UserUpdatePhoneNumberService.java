package com.example.team_project.domain.domain.user.service.update;

import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserUpdatePhoneNumberService {
    private final UserRepository userRepository;

    public UserUpdatePhoneNumberService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void updatePhoneNumber(Long id, String password, String name, String phoneNumber) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ID or Password"));
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid ID or Password");
        }
        user.updatePhoneNumber(name, phoneNumber);
    }
}
