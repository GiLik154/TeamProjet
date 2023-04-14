package com.example.team_project.controller.core.order.view;

import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.exception.OrderListNotFoundException;
import com.example.team_project.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order_list/view")
public class OrderListViewController {

    private final OrderRepository orderRepository;
    private final OrderListRepository orderListRepository;

    @GetMapping
    public ModelAndView view(@SessionAttribute("userId") Long userId) {
        ModelAndView modelAndView = new ModelAndView("thymeleaf/order/order_list");
        OrderList orderList = orderListRepository.findByUserId(userId).orElseThrow(OrderListNotFoundException::new);
        modelAndView.addObject("order_list", orderList);

        return modelAndView;
    }

    @GetMapping("/detail/{orderListId}")
    public ModelAndView detail(@PathVariable Long orderListId) {
        ModelAndView modelAndView = new ModelAndView("thymeleaf/order/order_list_detail");
        List<Order> orders = orderRepository.findByOrderListId(orderListId).orElseThrow(OrderNotFoundException::new);
        modelAndView.addObject("orders", orders);

        return modelAndView;
    }

}