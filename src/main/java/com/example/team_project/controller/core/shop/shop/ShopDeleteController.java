package com.example.team_project.controller.core.shop.shop;


import com.example.team_project.domain.domain.shop.shop.domain.Shop;
import com.example.team_project.domain.domain.shop.shop.domain.ShopRepository;
import com.example.team_project.domain.domain.shop.shop.service.delete.ShopDeleteService;
import com.example.team_project.domain.domain.shop.shop.service.dto.ShopJoinDto;
import com.example.team_project.exception.NotPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("shop/delete")
public class ShopDeleteController {

    private final ShopDeleteService shopDeleteService;
    private final ShopRepository shopRepository;

    @GetMapping("")
    public String delForm(@RequestParam("shopId") Long shopId, Model model) {
        model.addAttribute("shopIds", shopId);
        return "thymeleaf/shop/shopDeleteForm";

    }

    @PostMapping("")
    public String del(@RequestParam("shopId") Long shopId, @SessionAttribute("ownerId") String ownerId, @RequestParam("password") String password) {

            shopDeleteService.delete(shopId, ownerId, password);
            return "redirect:/";

    }
}
