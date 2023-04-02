package com.example.team_project.domain.domain.user.service.purchase;

import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;

public class UserPurchaseService {
    private final UserRepository userRepository;

    public UserPurchaseService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void purchase(Long userId, String item) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user id"));
        // 해당 사용자가 물건을 구매하는 로직
    }
}
