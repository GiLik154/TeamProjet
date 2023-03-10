package com.example.team_project.domain.user.controller;

import com.example.team_project.domain.user.dto.PostDto;
import com.example.team_project.domain.user.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
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
    public String write(@ModelAttribute PostDto postDto){
        joinService.write(postDto);
        return "thymeleaf/index";
    }

}
