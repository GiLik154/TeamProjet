package com.example.team_project.domain.domain.post.post.service.update;

import com.example.team_project.domain.domain.post.category.domain.PostCategory;
import com.example.team_project.domain.domain.post.category.domain.PostCategoryRepository;
import com.example.team_project.domain.domain.post.post.domain.Post;
import com.example.team_project.domain.domain.post.post.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostUpdateServiceImpl implements PostUpdateService {
    private final PostRepository postRepository;
    private final PostCategoryRepository postCategoryRepository;

    @Override
    public boolean update(Long postId, String content, String postCategory){
        Optional<Post>postOptional=postRepository.findById(postId);
        if(postOptional.isPresent()){
            Post post = postOptional.get();
            Optional<PostCategory>postCategoryOptional=postCategoryRepository.findByName(postCategory);
            if(postCategoryOptional.isPresent()) {
                PostCategory getPostCategory = postCategoryOptional.get();
                post.update(content, getTime(), getPostCategory);
            }
            return true;
        }
        return false;
    }
    private  String getTime() {
        LocalDateTime localDateTime = LocalDateTime.now();

        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:ss"));
    }
}
