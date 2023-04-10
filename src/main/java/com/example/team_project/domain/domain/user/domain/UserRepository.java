package com.example.team_project.domain.domain.user.domain;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.exception.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User save(User user);

    Optional<User> findByUserId(String userId);

    Optional<User> findByUserId(Long userId);

    Optional<User> findByUserIdAndPassword(String userId, String password);

//    @Query("SELECT u.userAddress FROM User u WHERE u.id = ?")
    List<UserAddress> findAddressListByUserId(Long userId);

    void delete(User user);

    default User validateUserId(Long userId){
        Optional<User> userOptional = findById(userId);
        if (userOptional.isPresent()){
            return userOptional.get();
        }
        throw new UserNotFoundException();
    }
}
