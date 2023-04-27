package com.example.team_project.controller.core.payment;

import com.example.team_project.domain.domain.payment.Service.PaymentService;
import com.example.team_project.domain.domain.payment.domain.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/payment/list")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;

    @GetMapping
    public String get(@SessionAttribute Long userId, Model model) {

        model.addAttribute("list", paymentRepository.findByUserId(userId));

        return "thymeleaf/payment/payment-list";
    }

    @DeleteMapping("/{paymentId}")
    public String delete(@SessionAttribute Long userId, @PathVariable Long paymentId) {
        paymentService.deletePayment(userId, paymentId);
        return "thymeleaf/payment/payment-list";
    }


}
