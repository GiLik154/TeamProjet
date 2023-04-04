package com.example.team_project.domain.domain.payment.domain;

import com.example.team_project.enums.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // 유저의 고유번호를 통해 결제수단을 조회
    List<Payment> findByUserId(Long userId);

    // 유저의 고유번호와 결제수단의 종류를 통해 결제수단을 조회
    Payment findByUserIdAndType(Long userId, PaymentType type);

    // 유저의 고유번호를 통해 결제수단을 삭제
    void deleteByUserId(Long userId);
}
