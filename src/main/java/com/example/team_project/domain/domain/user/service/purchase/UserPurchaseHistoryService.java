package com.example.team_project.domain.domain.user.service.purchase;

import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserPurchaseHistoryService {
    private final UserRepository userRepository;

    public UserPurchaseHistoryService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getPurchaseHistory(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user id"));
        // 해당 사용자의 구매 내역 조회 로직
        return null;
    }
}
