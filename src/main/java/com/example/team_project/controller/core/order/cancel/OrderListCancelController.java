package com.example.team_project.controller.core.order.cancel;

import com.example.team_project.domain.domain.order.list.service.OrderListCancelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order_list/cancel")
public class OrderListCancelController {

    private final OrderListCancelService orderListCancelService;


    @GetMapping("/{orderListId}")
    public ModelAndView cancel( @PathVariable Long orderListId) {
        orderListCancelService.cancel(orderListId);

        return new ModelAndView("redirect:/order_list/view/");
    }

}
