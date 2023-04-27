package com.example.team_project.domain.domain.payment.domain;

import com.example.team_project.enums.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByUserId(Long userId);

    Optional<Payment> findByUserIdAndId(Long userId, Long paymentId);

    List<Payment> findListByUserId(Long userId);
}
