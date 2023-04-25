package com.example.team_project.controller.core.order.cancel;

import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.item.service.OrderCancelService;
import com.example.team_project.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order/cancel")
public class OrderCancelController {

    private final OrderCancelService orderCancelService;
    private final OrderRepository orderRepository;

    @GetMapping("/{orderId}/{orderListId}")
    public ModelAndView cancelConfirm(@PathVariable Long orderId, @PathVariable Long orderListId) {

        ModelAndView modelAndView = new ModelAndView("thymeleaf/order/order_cancel_confirm");
        modelAndView.addObject("order_id", orderId);
        modelAndView.addObject("order_list_id", orderListId);

        return modelAndView;
    }

    @PostMapping
    public ModelAndView cancel(@SessionAttribute("userId") Long userId, @RequestParam Long orderId, @RequestParam Long orderListId) {

        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        orderCancelService.cancel(userId, order.getOrderToProduct().getId(), orderId, orderListId);

        return new ModelAndView("redirect:/order/view/detail/" + orderListId);

    }

}
