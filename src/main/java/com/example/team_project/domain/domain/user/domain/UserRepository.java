package com.example.team_project.domain.domain.user.domain;

import com.example.team_project.domain.domain.user.service.auth.dto.UserSignUpDTO;
import com.example.team_project.enums.UserGrade;
import com.example.team_project.exception.UserInfoAlreadyExistsException;
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

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

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

    /**
     * 아이디, 이메일, 전화번호 중복 확인
     * @param userSignUpDTO
     */
    default void duplication(UserSignUpDTO userSignUpDTO) {
        Optional<User> optionalUserId = findByUserId(userSignUpDTO.getUserId());
        Optional<User> optionalEmail = findByEmail(userSignUpDTO.getEmail());
        Optional<User> OptionalPhoneNumber = findByPhoneNumber(userSignUpDTO.getPhoneNumber());

        if (optionalUserId.isPresent()) {
            throw new UserInfoAlreadyExistsException("userId already exists");
        }

        if (optionalEmail.isPresent()) {
            throw new UserInfoAlreadyExistsException("email already exists");
        }

        if (OptionalPhoneNumber.isPresent()) {
            throw new UserInfoAlreadyExistsException("phoneNumber already exists");
        }

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

    boolean existsByUserId(String userId);

    boolean existsByEmail(String email);

}
