package com.example.team_project.domain.review.basereview.domain;

import com.example.team_project.domain.review.postreview.domain.PostReview;
import com.example.team_project.domain.review.productreview.domain.ProductReview;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
public class BaseReview {
    //리뷰의 고유번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String content;
    //리뷰 작성 시간
    private String time;
    //리뷰 이미지
    private String imagePath;
    //리뷰 삭제 여부
    private String situation;

    public BaseReview(){}
}
