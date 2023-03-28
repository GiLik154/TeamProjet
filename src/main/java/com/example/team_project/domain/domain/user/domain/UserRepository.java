package com.example.team_project.domain.domain.user.domain;

import com.example.team_project.exception.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    default User validateUserId(Long userId){
        Optional<User> userOptional = findById(userId);
        if (userOptional.isPresent()){
            return userOptional.get();
        }
        throw new UserNotFoundException();
    }
}
