package com.example.team_project.controller.core.shop.seller;

import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/seller/searchPassword")
public class SellerSearchPasswordController {

    private final SellerRepository sellerRepository;

    @GetMapping("")
    public String searchIdForm() {
        return "thymeleaf/seller/sellerSearchPasswordForm";
    }

    @PostMapping("")
    public String searchId(@RequestParam("ownerId") String ownerId, Model model) {
        Seller seller = sellerRepository.validateSeller(ownerId);
        model.addAttribute("ownerId", seller.getOwnerId());
        return "thymeleaf/seller/sellerEmailInputForm";
    }
}
