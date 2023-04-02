package com.example.team_project.domain.domain.user.domain;

import com.example.team_project.exception.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);

    User findByUserIdAndPassword(String userId, String password);

    default User validateUserId(Long userId){
        Optional<User> userOptional = findById(userId);
        if (userOptional.isPresent()){
            return userOptional.get();
        }
        throw new UserNotFoundException();
    }
}
