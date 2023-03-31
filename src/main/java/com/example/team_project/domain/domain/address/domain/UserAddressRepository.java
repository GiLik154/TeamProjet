package com.example.team_project.domain.domain.address.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    Optional<UserAddress> findByUserIdAndId(Long userId, Long addressId);
    List<UserAddress> findByUserId(Long userId);
}
