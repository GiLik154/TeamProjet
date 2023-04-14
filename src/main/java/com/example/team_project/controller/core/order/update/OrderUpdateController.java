package com.example.team_project.controller.core.order.update;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.item.service.OrderUpdateServiceImpl;
import com.example.team_project.domain.domain.order.list.service.OrderListUpdateServiceImpl;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.exception.InvalidAddressException;
import com.example.team_project.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequiredArgsConstructor
@RequestMapping("/order_list/update")
public class OrderUpdateController {

    private final OrderListUpdateServiceImpl orderListUpdateService;
    private final UserAddressRepository userAddressRepository;

    @GetMapping("/{userAddressId}")
    public ModelAndView updateForm(@PathVariable Long userAddressId, @SessionAttribute("userId") Long userId, @RequestParam Long orderListId) {
        UserAddress userAddress = userAddressRepository.findById(userAddressId).orElseThrow(InvalidAddressException::new);
        ModelAndView modelAndView = new ModelAndView("thymeleaf/order/order_address_update");
        modelAndView.addObject("userAddress", userAddress);
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("order_list_id", orderListId);

        return modelAndView;
    }

    @PostMapping("/{userAddressId}")
    public ModelAndView update(@PathVariable Long userAddressId, @RequestParam Long userId, @RequestParam Long orderListId) {
        UserAddress userAddress = userAddressRepository.findById(userAddressId).orElseThrow(InvalidAddressException::new);
        orderListUpdateService.update(userId, orderListId, userAddress);

        return new ModelAndView("redirect:/order_list/view/" + userId);


    }


}
