package com.example.team_project.controller.core.product;

import com.example.team_project.domain.domain.product.countduplication.domain.LikeCountCheck;
import com.example.team_project.domain.domain.product.countduplication.domain.LikeCountRepository;
import com.example.team_project.domain.domain.product.countduplication.service.LikeCountCheckService;
import com.example.team_project.domain.domain.product.countduplication.service.LikeCountDeleteService;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("update/like")
public class ProductLikeCountController {

    private final LikeCountCheckService likeCountCheckService;
    private final LikeCountDeleteService likeCountDeleteService;

    @GetMapping()
    public String likeCount(@SessionAttribute("userId")Long userId, @RequestParam("productId")Long productId){

        boolean delete = likeCountDeleteService.delete(productId,userId);

        if(delete){

        }else{
        likeCountCheckService.countCheck(userId,productId);

        }

        return "redirect:/product/detail/"+productId;



    }





}


