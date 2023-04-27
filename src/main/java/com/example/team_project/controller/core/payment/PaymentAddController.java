package com.example.team_project.controller.core.payment;

import com.example.team_project.controller.core.payment.dto.PaymentControllerDto;
import com.example.team_project.domain.domain.payment.Service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment/add")
public class PaymentAddController {

    private final PaymentService paymentService;

    @GetMapping
    public String get() {
        return "thymeleaf/payment/payment-add";
    }

    @PostMapping
    public String post(@SessionAttribute Long userId, @Valid PaymentControllerDto paymentControllerDto) {
        paymentService.addPayment(userId, paymentControllerDto.convertServiceDto());

        return "thymeleaf/payment/payment-add";
    }
}
