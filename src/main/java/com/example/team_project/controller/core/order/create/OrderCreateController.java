package com.example.team_project.controller.core.order.create;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.item.service.OrderCreateServiceImpl;
import com.example.team_project.domain.domain.payment.domain.Payment;
import com.example.team_project.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order/create")
public class OrderCreateController {

    private final OrderRepository orderRepository;
    private final OrderCreateServiceImpl orderCreateServiceImpl;

    @GetMapping("/{orderId}")
    public ModelAndView createForm(@PathVariable Long orderId, @RequestParam Long userId) {
        ModelAndView modelAndView = new ModelAndView("/thymeleaf/order/order_create");
        modelAndView.addObject("userId", userId.toString());
        modelAndView.addObject("order", orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new));

        return modelAndView;
    }

    @PostMapping("/{userId}")
    public ModelAndView create(@PathVariable Long userId,
                               @RequestParam Long productId,
                               @RequestParam int quantity,
                               @RequestBody Long userAddressId,
                               @RequestParam Long paymentId) {

        orderCreateServiceImpl.create(userId, productId, quantity, userAddressId, paymentId);

        return new ModelAndView("redirect:/order_list/view" + userId);

    }
}
