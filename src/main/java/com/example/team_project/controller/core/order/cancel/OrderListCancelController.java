package com.example.team_project.controller.core.order.cancel;

import com.example.team_project.domain.domain.order.list.service.OrderListCancelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order_list/cancel")
public class OrderListCancelController {

    private final OrderListCancelService orderListCancelService;


    @GetMapping("/{orderListId}")
    public ModelAndView cancelConfirm(@PathVariable Long orderListId) {
        ModelAndView modelAndView = new ModelAndView("thymeleaf/order/order_list_cancel_confirm");
        modelAndView.addObject("orderListId", orderListId);

        return modelAndView;
    }

    @PostMapping()
    public String cancel(@RequestParam Long orderListId) {
        orderListCancelService.cancel(orderListId);

        return "redirect:/main";


    }

}
