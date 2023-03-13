package com.example.team_project.domain.user.domain;

import com.example.team_project.controller.uesr.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
      boolean existsByName(String name);
      boolean existsByNameAndPassword(String name, String pass);

      boolean existsByPassword(String pass);
}
