package com.example.team_project.controller.core.product;

import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.product.product.service.dto.ProductDto;
import com.example.team_project.domain.domain.product.product.service.registration.ProductRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("product/registration")
public class ProductRegistrationController {

    private final ProductRegistrationService productRegistrationService;

    @GetMapping("")
    public String registrationForm() {
        return "thymeleaf/product/productRegistrationForm";
    }

    @PostMapping("")
    public String registration(@SessionAttribute("sellerId") Long sellerId, ProductDto productDto, MultipartFile multipartFile) {
        productRegistrationService.productRegistration(sellerId, productDto,multipartFile);

        return "redirect:/product/registration";


    }
}
