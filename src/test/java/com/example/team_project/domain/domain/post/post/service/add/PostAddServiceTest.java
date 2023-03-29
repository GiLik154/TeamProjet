package com.example.team_project.domain.domain.post.post.service.add;

import com.example.team_project.domain.domain.post.category.domain.PostCategory;
import com.example.team_project.domain.domain.post.category.domain.PostCategoryRepository;
import com.example.team_project.domain.domain.post.post.domain.Post;
import com.example.team_project.domain.domain.post.post.domain.PostRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostAddServiceTest {
    private final PostAddService postAddService;
    private final UserRepository userRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final PostRepository postRepository;
   @Autowired
    PostAddServiceTest(PostAddService postAddService, UserRepository userRepository, PostCategoryRepository postCategoryRepository, PostRepository postRepository) {
        this.postAddService = postAddService;
        this.userRepository = userRepository;
        this.postCategoryRepository = postCategoryRepository;
        this.postRepository = postRepository;
    }

    @Test
    void 게시글_추가_정상작동(){
        User user = new User("testName","testPassword");
        userRepository.save(user);

        PostCategory postCategory = new PostCategory("testCategory");
        postCategoryRepository.save(postCategory);

        String content = "testContent";
        String category = "testCategory";

        boolean isAdd = postAddService.add(user.getId(),content,category);

        Optional<Post> post = postRepository.findByUserId(user.getId());
        assertTrue(isAdd);
        assertEquals(post.get().getPostCategory().getName(),"testCategory");
        assertEquals(post.get().getContent(),"testContent");
        assertEquals(post.get().getUser().getId(),user.getId());
        assertEquals(post.get().getSituation(),"create");
    }
}