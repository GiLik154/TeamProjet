package com.example.team_project.controller.core.order.create;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.item.service.OrderCreateService;
import com.example.team_project.domain.domain.order.item.service.OrderCreateServiceImpl;
import com.example.team_project.domain.domain.payment.domain.Payment;
import com.example.team_project.domain.domain.payment.domain.PaymentRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.product.product.service.dto.ProductDto;
import com.example.team_project.exception.OrderNotFoundException;
import com.example.team_project.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order/create")
public class OrderCreateController {

    private final UserAddressRepository userAddressRepository;
    private final PaymentRepository paymentRepository;
    private final ProductRepository productRepository;
    private final OrderCreateService orderCreateService;

    @GetMapping
    public ModelAndView createForm(@RequestParam Long productId, @RequestParam Long userId) {
        ModelAndView modelAndView = new ModelAndView("/thymeleaf/order/order_create");
        List<UserAddress> userAddressList = userAddressRepository.findByUserId(userId);
        List<Payment> paymentList = paymentRepository.findByUserId(userId);
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

        modelAndView.addObject("userId", userId);
        modelAndView.addObject("user_address_list", userAddressList);
        modelAndView.addObject("payment_list", paymentList);
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @PostMapping("/{userId}")
    public ModelAndView create(@PathVariable Long userId,
                               @RequestParam Long productId,
                               @RequestParam int quantity,
                               @RequestParam Long userAddressId,
                               @RequestParam Long paymentId) {

        orderCreateService.create(userId, productId, quantity, userAddressId, paymentId);

        return new ModelAndView("redirect:/order_list/view" + userId);//결제페이지로 이동하게

    }
}
