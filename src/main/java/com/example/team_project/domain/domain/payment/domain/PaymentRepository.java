package com.example.team_project.domain.domain.payment.domain;

import com.example.team_project.enums.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByUserId(Long userId);
<<<<<<< HEAD
    Optional<Payment> findByUserIdAndId(Long userId, Long paymentId);

    List<Payment> findListByUserId(Long userId);
=======

//    Optional<Payment> findByOrderListIdAndPaymentId(Long orderListId, Long paymentId);
>>>>>>> 9f66fdff73508c5ec325ac35bf289ea1d681dbb4
}
