package com.example.team_project.controller.core.post;

import com.example.team_project.domain.domain.post.post.domain.PostRepository;
import com.example.team_project.domain.domain.post.post.service.PostDto;
import com.example.team_project.domain.domain.post.post.service.update.PostUpdateService;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post/update")
public class PostUpdateController {

    private final PostUpdateService postUpdateService;
    private final PostRepository postRepository;

    @GetMapping("")
    public String get(@RequestParam("postId") Long postId,
                      Model model){

        postRepository.findById(postId).ifPresent(post -> {
           model.addAttribute("post",post);
        });

        return "thymeleaf/post/update";
    }

    @PostMapping("")
    public String post(@SessionAttribute("userId") Long userId,
                       @RequestParam("postId") Long postId,
                       PostDto dto,
                       MultipartFile file){
        postUpdateService.update(userId,postId,dto,file);

        return "redirect:/post/list";
    }
}
