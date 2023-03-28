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
    @OneToOne(fetch = FetchType.LAZY)
    private BaseReview baseReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public PostReview() {
    }
}
