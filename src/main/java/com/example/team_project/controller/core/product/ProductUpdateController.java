package com.example.team_project.controller.core.product;

import com.example.team_project.domain.domain.product.product.service.dto.ProductDto;
import com.example.team_project.domain.domain.product.product.service.update.ProductUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("product/update")
public class ProductUpdateController {

    private final ProductUpdateService productUpdateService;

    @GetMapping("")
    public String updateForm(@RequestParam("productId") Long productId, Model model) {
        model.addAttribute("productId",productId);
        return "thymeleaf/product/productUpdateForm";
    }

    @PostMapping("")
    public String update(@SessionAttribute("sellerId") Long sellerId,
                         @RequestParam("password") String password,
                         @RequestParam("productId") Long productId,
                         ProductDto productDto) {
        productUpdateService.update(sellerId, productId, password, productDto);

        return "redirect:/product/seller/list";
    }

}
