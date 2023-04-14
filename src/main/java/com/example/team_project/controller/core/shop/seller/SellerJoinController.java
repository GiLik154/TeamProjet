package com.example.team_project.controller.core.shop.seller;

import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.service.dto.SellerJoinDto;
import com.example.team_project.domain.domain.shop.seller.service.join.SellerJoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/seller/join-form")
public class SellerJoinController {

    private final SellerJoinService sellerJoinService;

    @GetMapping("")
    public String joinForm() {
        return "thymeleaf/seller/sellerJoinForm";
    }

    @PostMapping("")
    public String join(SellerJoinDto sellerJoinDto) {
        sellerJoinService.sellerJoin(sellerJoinDto);
        return "redirect:/";
    }
}
