package com.example.team_project.domain.domain.user.domain;

import com.example.team_project.enums.UserGrade;
import com.example.team_project.exception.UserNotFoundException;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserIdAndPassword(String userId, String password);

    Optional<User> findByUserId(String userId);

    Optional<User> findByIdAndPassword(Long userId, String password);

    default User validateUserId(Long userId) {
        Optional<User> userOptional = findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new UserNotFoundException();
    }

    /**
     * User의 UserId를 검색해서
     * 존재 하지 않을 경우 Exception을 던짐
     * @param userId
     */
    default User validateUserId(String userId) {
        Optional<User> userOptional = findByUserId(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new UserNotFoundException();
    }

    default boolean checkUserCouponLevel(Long userId) {
        return validateUserId(userId).getUserGrade().equals(UserGrade.VIP);
    }

    boolean existsByUserId(String userId);

}
