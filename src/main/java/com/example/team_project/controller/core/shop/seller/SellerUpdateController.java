package com.example.team_project.controller.core.shop.seller;

import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.shop.seller.service.dto.SellerJoinDto;
import com.example.team_project.domain.domain.shop.seller.service.update.SellerUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("seller/update")
public class SellerUpdateController {

    private final SellerUpdateService sellerUpdateService;


    @GetMapping("")
    public String updateForm(@SessionAttribute("ownerId") String ownerId, Model model) {
        model.addAttribute("ownerId", ownerId);
        return "thymeleaf/seller/sellerUpdateForm";
    }

    @PostMapping("")
    public String update(@SessionAttribute("sellerId") Long sellerId,
                         @RequestParam("password") String password,
                         @RequestParam("ownerName") String ownerName,
                         @RequestParam("phoneNumber") String phoneNumber) {

        sellerUpdateService.sellerUpdate(sellerId, password, ownerName, phoneNumber);

        return "redirect:/";
    }

}
