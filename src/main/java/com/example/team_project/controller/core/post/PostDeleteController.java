package com.example.team_project.controller.core.post;

import com.example.team_project.domain.domain.post.post.service.delete.PostDeleteService;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post/delete")
public class PostDeleteController {

    private final PostDeleteService postDeleteService;

    @GetMapping("")
    public String get(@RequestParam("postId")Long postId,
                      Model model){
        model.addAttribute("postId",postId);

        return "thymeleaf/post/validate";
    }

    @PostMapping("")
    public String post(Long userId, @RequestParam("postId") Long postId){

        // user.vaildate(password); 예시
        postDeleteService.delete(151L,postId);
        return "redirect:/post/list";
    }
}
