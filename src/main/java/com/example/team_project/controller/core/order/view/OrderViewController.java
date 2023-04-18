package com.example.team_project.controller.core.order.view;

import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.exception.OrderListNotFoundException;
import com.example.team_project.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order/view")
public class OrderViewController {

    private final OrderRepository orderRepository;
    private final OrderListRepository orderListRepository;

    @GetMapping("/detail/{orderListId}")
    public ModelAndView detail(@PathVariable Long orderListId, @SessionAttribute("userId") Long userId) {
        ModelAndView modelAndView = new ModelAndView("thymeleaf/order/order_detail");
        List<Order> orders = orderRepository.findByOrderListId(orderListId).orElseThrow(OrderNotFoundException::new);
        OrderList orderList = orderListRepository.findById(orderListId).orElseThrow(OrderListNotFoundException::new);
        modelAndView.addObject("order_list", orderList);
        modelAndView.addObject("userId", userId.toString());
        modelAndView.addObject("orders", orders);

        return modelAndView;
    }
}
