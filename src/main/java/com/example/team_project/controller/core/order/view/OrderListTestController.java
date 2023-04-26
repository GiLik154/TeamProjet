package com.example.team_project.controller.core.order.view;

import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.exception.OrderListNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequiredArgsConstructor
@RequestMapping("/test")
public class OrderListTestController {

    private final OrderListRepository orderListRepository;
    @GetMapping
    public ModelAndView view(@SessionAttribute("userId") Long userId) {
        ModelAndView modelAndView = new ModelAndView("redirect:/order_list/view");

        OrderList orderList = orderListRepository.findByUserIdAndStatus(userId, true).orElseThrow(OrderListNotFoundException::new);
        Long orderListId = orderList.getId();
        modelAndView.addObject("orderListId", orderListId);

        return modelAndView;
    }
}
