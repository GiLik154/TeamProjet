package com.example.team_project.domain.domain.review.kinds.post.domain;

import com.example.team_project.domain.domain.post.post.domain.Post;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class PostReview {
    //리뷰의 고유번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 리뷰가 달린 게시글의 정보
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    public PostReview() {
    }

    public PostReview(Post post) {
        this.post = post;
    }

}
