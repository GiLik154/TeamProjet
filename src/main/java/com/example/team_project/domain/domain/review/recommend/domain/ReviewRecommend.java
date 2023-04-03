package com.example.team_project.domain.domain.review.recommend.domain;

import com.example.team_project.domain.domain.review.base.domain.BaseReview;
import com.example.team_project.domain.domain.user.domain.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ReviewRecommend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    /**
     * 추천을 한 유저의 정보
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    /**
     * 추천을 받은 리뷰의 정보
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private BaseReview baseReview;

    /**
     * 추천 여부(추천,비추천,취소)
     */
    private String recommend;

    public ReviewRecommend() {
    }

    /**
     * 유저의 정보,리뷰의 정보,추천 여부 로 객체 생성
     */
    public ReviewRecommend(User user, BaseReview baseReview,String recommend){
        this.user=user;
        this.baseReview=baseReview;
        this.recommend=recommend;
    }

    /**
     * 추천여부의 변경
     */
    public void update(String recommend){
        this.recommend=recommend;
    }
}
