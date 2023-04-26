package com.example.team_project.domain.domain.user.domain;

import com.example.team_project.enums.SocialType;
import com.example.team_project.enums.UserGrade;
import com.example.team_project.exception.UserNotFoundException;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByRefreshToken(String refreshToken);

    /**
     * 소셜 타입과 소셜의 식별값으로 회원 찾는 메소드
     * 정보 제공을 동의한 순간 DB에 저장해야하지만, 아직 추가 정보(전화번호)를 입력받지 않았으므로
     * 유저 객체는 DB에 있지만, 추가 정보가 빠진 상태이다.
     * 따라서 추가 정보를 입력받아 회원 가입을 진행할 때 소셜 타입, 식별자로 해당 회원을 찾기 위한 메소드
     */
    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

    Optional<User> findByUserIdAndPassword(String userId, String password);

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
