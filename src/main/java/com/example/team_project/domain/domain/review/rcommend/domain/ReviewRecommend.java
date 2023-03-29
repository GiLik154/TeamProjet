package com.example.team_project.domain.domain.review.rcommend.domain;

import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import com.example.team_project.domain.domain.user.domain.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ReviewRecommend {
    //리뷰의 고유번호
    @Id
    private Long base_review;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private BaseReview baseReview;

    //유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    //추천 여부
    private Boolean recommend;

    public ReviewRecommend() {
    }
}
