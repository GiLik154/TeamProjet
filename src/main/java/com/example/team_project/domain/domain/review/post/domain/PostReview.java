package com.example.team_project.domain.domain.review.post.domain;

import com.example.team_project.domain.domain.post.post.domain.Post;
import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class PostReview {
    //리뷰의 고유번호
    @Id
    private Long baseReviewId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="base_review_id")
    private BaseReview baseReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public PostReview() {
    }
}
