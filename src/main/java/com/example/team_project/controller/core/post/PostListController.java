package com.example.team_project.controller.core.post;

import com.example.team_project.domain.domain.post.post.domain.Post;
import com.example.team_project.domain.domain.post.post.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post/list")
public class PostListController {

    private final PostRepository postRepository;

    @GetMapping("")
    public String get(Model model){

        List<Post>postList = postRepository.findBySituation("create");
        model.addAttribute("postList",postList);

        return "thymeleaf/post/list";
    }
}
