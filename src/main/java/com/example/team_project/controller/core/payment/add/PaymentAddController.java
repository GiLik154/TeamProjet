package com.example.team_project.controller.core.payment.add;

import com.example.team_project.domain.domain.payment.Service.PaymentService;
import com.example.team_project.enums.PaymentType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
@SessionAttributes("userId")
public class PaymentAddController {
    private final PaymentService paymentService;

    @PostMapping("/register")
    public String registerPayment(@RequestParam("userId") Long userId,
                                  @RequestParam("paymentType") PaymentType paymentType,
                                  @RequestParam("number") String number) {
        paymentService.registerPayment(userId, paymentType, number);
        return "redirect:/payment/success";
    }

}
