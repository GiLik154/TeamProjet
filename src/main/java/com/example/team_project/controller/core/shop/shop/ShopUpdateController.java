package com.example.team_project.controller.core.shop.shop;

import com.example.team_project.domain.domain.shop.shop.service.update.ShopUpdateService;
import lombok.RequiredArgsConstructor;
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
        System.out.println("수정이동");
        return "thymeleaf/shop/shopUpdateForm";
    }


    @PostMapping("")
    public String update(@SessionAttribute("sellerId") Long sellerId,
                         @RequestParam("password") String password,
                         @RequestParam("shopName") String shopName,
                         @RequestParam("shopAddress") String shopAddress,
                         @RequestParam("shopId")Long shopId){



        System.out.println("수정실행: " + sellerId+password+shopName+shopAddress+shopId);

        shopUpdateService.shopUpdate(sellerId,shopId,password,shopName,shopAddress);
        return "redirect:/shop/list";


    }

}
