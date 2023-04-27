package com.example.team_project.domain.domain.user.domain;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.payment.domain.Payment;
import com.example.team_project.enums.UserGrade;
import com.example.team_project.exception.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByUserId(String userId);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByRefreshToken(String refreshToken);
    Optional<User> findByUserIdAndPassword(String userId, String password);
    Optional<User> findByIdAndPassword(Long id, String password);




    default User validateUserId(Long userId) {
        Optional<User> userOptional = findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new UserNotFoundException();
    }

    default void verifyUserId(Long userId) {
        Optional<User> userOptional = findById(userId);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }
    }

    default boolean checkUserCouponLevel(Long userId) {
        return validateUserId(userId).getUserGrade().equals(UserGrade.VIP);
    }

}
