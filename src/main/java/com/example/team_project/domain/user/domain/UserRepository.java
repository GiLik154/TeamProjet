package com.example.team_project.domain.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    /** 유저 이름(아이디) 찾기 **/
    Optional<User> findByName(String userName);
    Optional<User> findByNameAndPassword(String userName , String userPassword);

}
