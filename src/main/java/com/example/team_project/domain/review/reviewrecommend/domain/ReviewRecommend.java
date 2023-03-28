package com.example.team_project.domain.review.reviewrecommend.domain;

import com.example.team_project.domain.review.basereview.domain.BaseReview;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ReviewRecommend {
    //리뷰의 고유번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rev")
    private BaseReview baseReview;
    //추천 여부
    private Boolean recommend;

    public ReviewRecommend(){}
}
