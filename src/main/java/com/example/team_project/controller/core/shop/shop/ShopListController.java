package com.example.team_project.controller.core.shop.shop;

import com.example.team_project.domain.domain.shop.shop.domain.Shop;
import com.example.team_project.domain.domain.shop.shop.domain.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/shop/list")
public class ShopListController {

    private final ShopRepository shopRepository;

    @GetMapping("")
    public String list (@SessionAttribute("sellerId")Long sellerId, Model model){
        List<Shop> shopList = shopRepository.findBySellerId(sellerId);
        model.addAttribute("shopList",shopList);
        return "thymeleaf/shop/shopList";
    }
}
