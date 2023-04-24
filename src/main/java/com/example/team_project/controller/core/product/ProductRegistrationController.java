package com.example.team_project.controller.core.product;

import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.product.product.service.dto.ProductDto;
import com.example.team_project.domain.domain.product.product.service.registration.ProductRegistrationService;
import com.example.team_project.exception.MinMaxException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
@RequestMapping("/product/registration")
public class ProductRegistrationController {

    private final ProductRegistrationService productRegistrationService;

    @GetMapping("")
    public String registrationForm() {
        return "thymeleaf/product/productRegistrationForm";
    }

    @PostMapping("")
    public String registration(@SessionAttribute("sellerId") Long sellerId, @Valid ProductDto productDto, MultipartFile multipartFile) {
        System.out.println("재고:" + productDto.getStock());
        if(productDto.getStock() < 0){

        }

        productRegistrationService.productRegistration(sellerId, productDto, multipartFile);

        return "redirect:/product/seller/list";
    }
}
