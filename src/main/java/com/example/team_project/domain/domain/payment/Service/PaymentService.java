package com.example.team_project.domain.domain.payment.Service;

import com.example.team_project.domain.domain.payment.domain.Payment;
import com.example.team_project.domain.domain.payment.domain.PaymentRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.PaymentType;
import com.example.team_project.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    public Payment addPayment(Long userId, String paymentType, String cardNumber) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());

        Payment payment = new Payment(user, PaymentType.valueOf(paymentType), cardNumber);
        return paymentRepository.save(payment);
    }

    public void deletePayment(Long id, Long userId) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);
        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
            if (payment.getUser().getId().equals(userId)) { // User ID -> User 객체에서 ID로 변경
                paymentRepository.deleteById(id);
            } else {
                throw new IllegalArgumentException("This payment method does not belong to this user.");
            }
        } else {
            throw new NoSuchElementException("Payment method not found.");
        }
    }

    public List<Payment> getPaymentsByUserId(Long userId) {
        return paymentRepository.findByUserId(userId);
    }


}
