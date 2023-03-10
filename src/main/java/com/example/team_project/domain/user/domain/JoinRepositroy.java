package com.example.team_project.domain.user.domain;

import com.example.team_project.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinRepositroy extends JpaRepository<User, Long> {
}
