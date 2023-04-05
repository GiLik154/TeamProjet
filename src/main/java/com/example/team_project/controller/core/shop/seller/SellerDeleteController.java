package com.example.team_project.controller.core.shop.seller;


import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.shop.seller.service.delete.SellerDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("seller/delete")
public class SellerDeleteController {

    private final SellerDeleteService sellerDeleteService;
    private final SellerRepository sellerRepository;

    @GetMapping("")
    public String delForm(@SessionAttribute("sellerId") Long sellerId, Model model){
        model.addAttribute("sellerId",sellerId);
        return "thymeleaf/seller/sellerDeleteForm";
    }

    @PostMapping("")
    public String delete(@SessionAttribute("sellerId") Long sellerId, @RequestParam("password") String password){
        Optional<Seller> seller = sellerRepository.findById(sellerId);
        sellerDeleteService.delete(sellerId,password,seller.get().getOwnerId());

        return "thymeleaf/seller/sellerIndex";


    }

}
