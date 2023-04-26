package com.example.team_project.controller.core.order.create;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
import com.example.team_project.domain.domain.order.item.service.OrderCreateService;
import com.example.team_project.domain.domain.payment.domain.Payment;
import com.example.team_project.domain.domain.payment.domain.PaymentRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;


@Controller
@RequiredArgsConstructor
@RequestMapping("/order/create")
public class OrderCreateController {

    private final ProductRepository productRepository;
    private final OrderCreateService orderCreateService;

    @GetMapping("/{productId}")
    public ModelAndView createForm(@PathVariable Long productId,
                                   @RequestParam("salesCount") int quantity) {
        ModelAndView modelAndView = new ModelAndView("thymeleaf/order/order_create");
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

        modelAndView.addObject("product", product);
        modelAndView.addObject("quantity", quantity);

        return modelAndView;
    }

    @PostMapping
    public ModelAndView create(@SessionAttribute("userId") Long userId,
                               @Validated OrderCreateDto orderCreateDto,
                               @CookieValue(name = "couponName", required = false) String couponName,
                               HttpSession httpSession) {

        Long userCouponId = (Long) httpSession.getAttribute(couponName);

        System.out.println(userCouponId + "이게 놀임?");

        Long order = orderCreateService.create(userId,
                orderCreateDto.getProductId(),
                orderCreateDto.getQuantity(),
                userCouponId);


        return new ModelAndView("redirect:/order_list/view");
    }
}
