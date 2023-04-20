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

    default User validateUserId(Long userId) {
        Optional<User> userOptional = findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new UserNotFoundException();
    }

    Optional<User> findByUserId(String userId);

    default User validateUserId(String userId) {
        Optional<User> userOptional = findByUserId(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new UserNotFoundException();
    }

    /**
     * 로그인 시에 User의 Role을 같이 로딩하는 메서드
     * social 속성값이 false인 사용자들만을 대상으로 처리
     */
    @EntityGraph(attributePaths = "roleSet")
    @Query("select u from User u where u.userId = :userId and u.social = false ")
    Optional<User> getWithRoles(String userId);

    default boolean checkUserCouponLevel(Long userId) {
        return validateUserId(userId).getUserGrade().equals(UserGrade.VIP);
    }

}
