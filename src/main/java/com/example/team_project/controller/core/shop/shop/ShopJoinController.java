package com.example.team_project.controller.core.shop.shop;

import com.example.team_project.domain.domain.shop.shop.service.dto.ShopJoinDto;
import com.example.team_project.domain.domain.shop.shop.service.join.ShopJoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/shop/join")
public class ShopJoinController {

    private final ShopJoinService shopJoinService;

    @GetMapping("")
    public String joinForm(@SessionAttribute("sellerId") Long sellerId, @SessionAttribute("ownerId")String ownerId, Model model) {
        model.addAttribute("sellerId",sellerId);
        model.addAttribute("ownerId",ownerId);
        return "thymeleaf/shop/shopJoinForm";
    }

    @PostMapping("")
    public String join(@SessionAttribute("sellerId")Long sellerId,ShopJoinDto shopJoinDto){
        shopJoinService.shopJoin(sellerId,shopJoinDto);
        return "thymeleaf/seller/sellerIndex";
    }
}
