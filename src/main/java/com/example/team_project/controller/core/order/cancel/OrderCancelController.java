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

    @PostMapping
    public ModelAndView cancel(@RequestParam Long orderId, @RequestParam Long orderListId) {

        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        orderCancelService.cancel(order.getOrderToProduct().getId(), orderId);

        return new ModelAndView("redirect:/order/view/detail/" + orderListId);

    }

}
