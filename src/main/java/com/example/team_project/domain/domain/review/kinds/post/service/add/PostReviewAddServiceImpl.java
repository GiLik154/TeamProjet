package com.example.team_project.domain.domain.review.kinds.post.service.add;

import com.example.team_project.domain.domain.post.post.domain.Post;
import com.example.team_project.domain.domain.post.post.domain.PostRepository;
import com.example.team_project.domain.domain.review.base.domain.BaseReviewRepository;
import com.example.team_project.domain.domain.review.base.domain.ReviewToKinds;
import com.example.team_project.domain.domain.review.kinds.ReviewJoinKindsService;
import com.example.team_project.domain.domain.review.kinds.post.domain.PostReview;
import com.example.team_project.domain.domain.review.kinds.post.domain.PostReviewRepository;
import com.example.team_project.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("PostReview")
@Transactional
@RequiredArgsConstructor
public class PostReviewAddServiceImpl implements ReviewJoinKindsService {
    private final PostReviewRepository postReviewRepository;
    private final PostRepository postRepository;

    @Override
    public ReviewToKinds returnReviewToKindsEntity(Long postId) {
        return new ReviewToKinds(add(postId));
    }

    /**
     * PostReview 를 생성 후 PostReview 반환해줌
     * */
    private PostReview add(Long postId) {
        PostReview postReview = new PostReview(post(postId));
        postReviewRepository.save(postReview);
        return postReview;
    }

    /**
     * Post 찾은 후 반환해줌 없을시 오류
     * */
    private Post post(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
    }
}
