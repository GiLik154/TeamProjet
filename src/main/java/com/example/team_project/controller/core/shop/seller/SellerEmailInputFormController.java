package com.example.team_project.controller.core.shop.seller;

import com.example.team_project.domain.domain.authentication.service.MailService;
import com.example.team_project.domain.domain.authentication.service.dto.MailDto;
import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.exception.SellerDuplicateSellerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/seller/sellerEmailInputForm")
public class SellerEmailInputFormController {

    private final MailService mailService;
    private final SellerRepository sellerRepository;

    @PostMapping("")
    public String searchId(@RequestParam("sellerEmail") String sellerEmail, @RequestParam("sellerDomain") String domain, @RequestParam("ownerId") String ownerId) {
        String title = ("비밀번호 찾기 토큰입니다.");
        String email = sellerEmail + "@" + domain;

        System.out.println("-----" + email+ "-----" +ownerId);


            mailService.sendEmailToken(email, title, ownerId);

            return "thymeleaf/seller/sellerEmailInputForm";
        }




}
