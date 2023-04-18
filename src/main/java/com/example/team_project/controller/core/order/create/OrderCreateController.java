package com.example.team_project.controller.core.order.create;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.item.service.OrderCreateService;
import com.example.team_project.domain.domain.order.item.service.OrderCreateServiceImpl;
import com.example.team_project.domain.domain.payment.domain.Payment;
import com.example.team_project.domain.domain.payment.domain.PaymentRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.product.product.service.dto.ProductDto;
import com.example.team_project.exception.InvalidAddressException;
import com.example.team_project.exception.OrderNotFoundException;
import com.example.team_project.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
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

//    @GetMapping("/{productId}/{salesCount}")
    @GetMapping("/{productId}")
    public ModelAndView createForm(@PathVariable Long productId, @SessionAttribute("userId") Long userId, @RequestParam("salesCount") int quantity) {
        ModelAndView modelAndView = new ModelAndView("thymeleaf/order/order_create");
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        List<Payment> paymentList = paymentRepository.findByUserId(userId);
        List<UserAddress> userAddressList = userAddressRepository.findByUserId(userId);

        if (userAddressList.isEmpty()) {
            throw new InvalidAddressException();
        }

        modelAndView.addObject("userId", userId);
        modelAndView.addObject("user_address_list", userAddressList);
        modelAndView.addObject("payment_list", paymentList);
        modelAndView.addObject("product", product);
        modelAndView.addObject("quantity", quantity);

        return modelAndView;
    }

    @PostMapping
    public ModelAndView create(@SessionAttribute("userId") Long userId, @Validated OrderCreateDto orderCreateDto) {

        Order order = orderCreateService.create(userId,
                orderCreateDto.getProductId(),
                orderCreateDto.getQuantity(),
                orderCreateDto.getUserAddressId(),
                orderCreateDto.getPaymentId());

        return new ModelAndView("redirect:/order_list/view" + order.getId());//결제페이지로 이동하게 바꿔야함
    }
}
