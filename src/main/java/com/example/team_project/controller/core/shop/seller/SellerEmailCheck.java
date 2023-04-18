package com.example.team_project.controller.core.shop.seller;

import com.example.team_project.domain.domain.authentication.domain.ConfirmationTokenRepository;
import com.example.team_project.domain.domain.authentication.service.MailService;
import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
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
@RequestMapping("/seller/SellerEmailCheck")
public class SellerEmailCheck {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final SellerRepository sellerRepository;

    @PostMapping("")
    public String emailCheck(@RequestParam("token") String token, @RequestParam("ownerId") String ownerId, Model model) {

        System.out.println("==토큰번호===" + token);

        confirmationTokenRepository.validate(token);

        Optional<Seller> seller = sellerRepository.findByOwnerId(ownerId);

        model.addAttribute("ownerId", seller.get().getOwnerId());
        return "thymeleaf/seller/sellerPasswordUpdateForm";

    }
}
