package com.example.team_project.domain.user.controller;

import com.example.team_project.domain.user.dto.PostDto;
import com.example.team_project.domain.user.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/team")
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @GetMapping("/joinuser")
    public String writer(){
        return "thymeleaf/joinuser";
    }

    @PostMapping("/joinuser")
    public String write(@ModelAttribute PostDto postDto, Model model,String name,String password){
        try {
            boolean isJoine = joinService.write(postDto);
            if (isJoine) {
                return "thymeleaf/index";
            } else {
                model.addAttribute("errorMsg","이름이 중복되었습니다");
                return "thymeleaf/joinuser";
            }
        } catch (DuplicateKeyException e) {
            model.addAttribute("errorMsg","이름이 중복되었습니다");
            return "thymeleaf/joinuser";
        }

    }

}
