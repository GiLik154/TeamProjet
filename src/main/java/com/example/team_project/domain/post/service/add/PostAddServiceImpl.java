package com.example.team_project.domain.post.service.add;

import com.example.team_project.domain.postcategory.domain.PostCategory;
import com.example.team_project.domain.postcategory.domain.PostCategoryRepository;
import com.example.team_project.domain.post.domain.Post;
import com.example.team_project.domain.post.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostAddServiceImpl implements PostAddService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostCategoryRepository postCategoryRepository;

    @Override
    public boolean add(Long userId, String content, String postCategory){
        Optional<User>userOptional=userRepository.findById(userId);
        Optional<PostCategory>postCategoryOptional=postCategoryRepository.findByName(postCategory);
        if(userOptional.isPresent()) {
            Post post = new Post(content, getTime(), userOptional, postCategoryOptional);
            postRepository.save(post);
            return true;
        }
        return false;
    }
    private  String getTime() {
        LocalDateTime localDateTime = LocalDateTime.now();

        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:ss"));
    }
}
