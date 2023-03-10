package com.example.team_project.domain.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    long countByNameAndPassword(String name, String password);
    Long countByName(String name);


    boolean existsByNameAndPassword(String name,String password);

    boolean existsByNameAndAndPassword(String name, String password);
}