package com.example.team_project.domain.review.postreview.domain;

import com.example.team_project.domain.post.domain.Post;
import com.example.team_project.domain.review.basereview.domain.BaseReview;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class PostReview extends BaseReview {
    //리뷰의 고유번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    public PostReview(){}
}
