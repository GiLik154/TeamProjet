package com.example.team_project.controller.core.post;

import com.example.team_project.domain.domain.post.post.service.PostDto;
import com.example.team_project.domain.domain.post.post.service.add.PostAddService;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.UserGrade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post/add")
public class PostAddController {
    private final PostAddService postAddService;

    @GetMapping("")
    public String get(){
        return "thymeleaf/post/add";
    }

    /**
     * 로그인 처리 기능이 구현되면 userId 세션으로 가지고 올 예정
     */
    @PostMapping("")
    public String post(@SessionAttribute("userId") Long userId, PostDto dto,
                       MultipartFile file){
        postAddService.add(userId,dto,file);
        return "redirect:/post/list";
    }
}
