package com.example.team_project.controller.core.order.payment;

import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.item.service.OrderCompletionService;
import com.example.team_project.domain.domain.payment.domain.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order/payment")
public class OrderPaymentController {

    private final UserAddressRepository userAddressRepository;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final OrderCompletionService orderCompletionService;

    @GetMapping
    public ModelAndView get(@SessionAttribute("userId") Long userId) {
        ModelAndView modelAndView = new ModelAndView("thymeleaf/order/order_payment");
        List<Order> orders = orderRepository.findByStatusOrderedAndUserId(userId);

        modelAndView.addObject("orders", orders);
        modelAndView.addObject("userAddressList", userAddressRepository.findByUserId(userId));
        modelAndView.addObject("paymentList", paymentRepository.findListByUserId(userId));

        return modelAndView;
    }

    @PostMapping()
    public String post(@SessionAttribute("userId") Long userId,
                       @RequestParam Long userAddressId,
                       @RequestParam Long paymentId,
                       @RequestParam Long orderListId) {
        orderCompletionService.processOrderPayment(userId, userAddressId, paymentId, orderListId);

        return "redirect:/order_list/view";
    }

}
