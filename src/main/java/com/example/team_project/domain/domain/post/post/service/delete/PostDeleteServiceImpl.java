package com.example.team_project.domain.domain.post.post.service.delete;

import com.example.team_project.domain.domain.post.post.domain.Post;
import com.example.team_project.domain.domain.post.post.domain.PostRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostDeleteServiceImpl implements PostDeleteService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptEncoder;

    @Override
    public boolean delete(Long postId, String password) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            Optional<User> userOptional = userRepository.findById(post.getUser().getId());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
//                if (!user.isValidPassword(bCryptEncoder, password) {
//                    throw new BadCredentialsException("Invalid User Password");
//                }
                post.delete();
                return true;
            }
        }
        return false;
    }
}
