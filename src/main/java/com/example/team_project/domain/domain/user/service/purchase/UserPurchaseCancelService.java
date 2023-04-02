package com.example.team_project.domain.domain.user.service.purchase;

import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;

public class UserPurchaseCancelService {
    private final UserRepository userRepository;

    public UserPurchaseCancelService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void cancelPurchase(Long userId, String item) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user id"));
        // 해당 사용자의 물건 구매 취소 로직
    }
}
