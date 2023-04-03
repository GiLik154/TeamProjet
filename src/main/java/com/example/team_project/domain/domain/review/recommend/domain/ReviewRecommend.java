package com.example.team_project.domain.domain.review.recommend.domain;

import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import com.example.team_project.domain.domain.user.domain.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ReviewRecommend {
    //리뷰의 고유번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    //유저
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private BaseReview baseReview;

    //추천 여부
    private String recommend;

    public ReviewRecommend() {
    }

    public ReviewRecommend(User user, BaseReview baseReview,String recommend){
        this.user=user;
        this.baseReview=baseReview;
        this.recommend=recommend;
    }

    public void update(String recommend){
        this.recommend=recommend;
    }
}
