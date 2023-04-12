package com.example.team_project.controller.core.shop.shop;

import com.example.team_project.domain.domain.shop.shop.service.update.ShopUpdateService;
import com.example.team_project.exception.ShopIncorrectUpdatePasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("shop/update")
public class ShopUpdateController {

    private final ShopUpdateService shopUpdateService;


    @GetMapping("")
    public String updateForm(@SessionAttribute("ownerId")String ownerId,@RequestParam("shopId")Long shopId, Model model){
        model.addAttribute("ownerId",ownerId);
        model.addAttribute("shopId",shopId);
        return "thymeleaf/shop/shopUpdateForm";
    }


    @PostMapping("")
    public String update(@SessionAttribute("sellerId") Long sellerId,
                         @RequestParam("password") String password,
                         @RequestParam("shopName") String shopName,
                         @RequestParam("shopAddress") String shopAddress,
                         @RequestParam("shopId")Long shopId){

        try{
            shopUpdateService.shopUpdate(sellerId,shopId,password,shopName,shopAddress);

            return "redirect:/shop/list";

        }catch (BadCredentialsException e){
            throw new ShopIncorrectUpdatePasswordException("비밀번호 틀림 . 다시 시도해주세요.");
        }


    }

}
