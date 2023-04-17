package com.example.team_project.controller.core.shop.seller;

import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.exception.SellerNameNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/seller/searchId")
public class SellerSearchIdController {

    private final SellerRepository sellerRepository;

    @GetMapping("")
    public String searchIdForm() {
        return "thymeleaf/seller/sellerSearchIdForm";
    }

    @PostMapping("")
    public String searchId(@RequestParam("ownerName") String ownerName, @RequestParam("phoneNumber") String phoneNumber, Model model) {
        try {
            Optional<Seller> seller = sellerRepository.findByOwnerNameAndPhoneNumber(ownerName, phoneNumber);
            model.addAttribute("sellerInformation", seller);

            return "thymeleaf/seller/sellerId";

        } catch (SellerNameNotFoundException ex) {
            throw new SellerNameNotFoundException("존재하지 않는 이름입니다.");
        }


    }
}
