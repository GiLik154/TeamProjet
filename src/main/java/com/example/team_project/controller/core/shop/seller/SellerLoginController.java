package com.example.team_project.controller.core.shop.seller;

import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.shop.seller.service.dto.SellerJoinDto;
import com.example.team_project.domain.domain.shop.seller.service.login.SellerLoginService;
import com.example.team_project.exception.SellerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequiredArgsConstructor
@RequestMapping("seller/login-form")
@SessionAttributes({"sellerId","ownerId"})
public class SellerLoginController {

    private final SellerRepository sellerRepository;
    private final SellerLoginService sellerLoginService;


    @GetMapping("")
    public String loginForm(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "thymeleaf/seller/sellerLoginForm";
    }


    @PostMapping("")
    public String login(@RequestParam("ownerId") String ownerId, @RequestParam("password") String password, Model model) {
        //오너 아이디 있나 검사
        Seller seller = sellerRepository.findByOwnerId(ownerId).orElseThrow(() ->
                new SellerNotFoundException("아이디가없음"));
        try {
            //로그인시도
            sellerLoginService.sellerLogin(ownerId, password);
            model.addAttribute("ownerId", ownerId);
            model.addAttribute("sellerId", seller.getId());

            //성공하면 seller 메인페이지로이동
            return "thymeleaf/seller/sellerIndex";

        } catch (BadCredentialsException e) {
            //실패시 출력
            throw new BadCredentialsException("로그인 실패하였습니다. 다시 시도해주세요.");

        }
    }

}
