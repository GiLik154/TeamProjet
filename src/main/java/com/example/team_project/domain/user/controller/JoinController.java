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
    public String joinForm(){
        return "thymeleaf/joinuser";
    }

    @PostMapping("/joinuser")
    public String join(@ModelAttribute PostDto postDto, Model model) {
        boolean joined = joinService.join(postDto);
        if (joined) {
            return "thymeleaf/index";
        } else {
            throw new DuplicateKeyException("이름이중복되었습니다.");
        }
    }


}
