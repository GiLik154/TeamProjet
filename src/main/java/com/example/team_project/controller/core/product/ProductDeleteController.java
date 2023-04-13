package com.example.team_project.controller.core.product;


import com.example.team_project.domain.domain.product.product.service.delete.ProductDeleteService;
import com.example.team_project.exception.NotPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product/delete")
public class ProductDeleteController {

    private final ProductDeleteService productDeleteService;

    @GetMapping("")
    public String delForm(@RequestParam("productId") Long productId, Model model) {
        model.addAttribute("productId", productId);
        return "thymeleaf/product/productDeleteForm";
    }

    @PostMapping("")
    public String del(@SessionAttribute("sellerId") Long sellerId,
                      @RequestParam("productId") Long productId,
                      @RequestParam("password") String password) {

        try {
            productDeleteService.delete(sellerId, productId, password);
            return "redirect:/product/seller/list";

        } catch (NotPasswordException ex) {
            throw new NotPasswordException("비밀번호를 잘못 입력했습니다.");
        }

    }
}
