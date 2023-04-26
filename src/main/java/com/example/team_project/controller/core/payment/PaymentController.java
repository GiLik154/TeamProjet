package com.example.team_project.controller.core.payment;

import com.example.team_project.domain.domain.payment.Service.PaymentService;
import com.example.team_project.domain.domain.payment.domain.Payment;
import com.example.team_project.domain.domain.payment.domain.PaymentRepository;
import com.example.team_project.enums.PaymentType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;

    @GetMapping("/payment/list")
    public String getPaymentList(Model model, @RequestParam Long userId, HttpSession session) {
        List<Payment> payments = paymentService.getPaymentList(userId);
        model.addAttribute("payments", payments);
        return "thymeleaf/payment/add";
    }

    @PostMapping("/payment/add")
    public String addPayment(@RequestParam Long userId, @RequestParam PaymentType paymentType, @RequestParam String number) {
        paymentService.addPayment(userId, paymentType, number);
        return "redirect:/payment/list/{userId}";
    }

    @GetMapping("/payment/update/{paymentId}")
    public String showUpdateForm(@RequestParam Long userId, @PathVariable Long paymentId, Model model) {
        Payment payment = paymentRepository.findByUserIdAndId(userId, paymentId).orElseThrow();
        model.addAttribute("payment", payment);
        return "updatePayment";
    }

    @PostMapping("/payment/update/{paymentId}")
    public String updatePayment(@RequestParam Long userId, @PathVariable Long paymentId, @RequestParam PaymentType paymentType, @RequestParam String number) {
        paymentService.updatePayment(userId, paymentId, paymentType, number);
        return "redirect:/payment/list/{userId}";
    }

    @GetMapping("/payment/delete/{paymentId}")
    public String deletePayment(@RequestParam Long userId, @PathVariable Long paymentId) {
        paymentService.deletePayment(userId, paymentId);
        return "redirect:/payment/list/{userId}";
    }
}
