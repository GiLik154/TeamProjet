package com.example.team_project.domain.user.domain;

import com.example.team_project.domain.user.dto.PostDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Long countByNameAndPassword(String name,String password);
    Long countByName(String name);

    User findByName(String test123);
}