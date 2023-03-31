package com.example.team_project.domain.domain.review.base.domain;

import com.example.team_project.domain.domain.image.ImageUpload;
import com.example.team_project.domain.domain.user.domain.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class BaseReview implements ImageUpload {
    //리뷰의 고유번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //유저
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private String content;
    //리뷰 작성 시간
    private String time;
    //리뷰 이미지
    private String imagePath;
    //리뷰 삭제 여부
    private String situation;
    //리뷰의 카테고리 종류
    @OneToOne(cascade = CascadeType.PERSIST)
    private ReviewToKinds reviewToKinds;

    protected BaseReview() {
    }

    public BaseReview(User user, String content, String time, String imagePath, ReviewToKinds reviewToKinds) {
        this.user = user;
        this.content = content;
        this.time = time;
        this.situation = "create";
        this.imagePath = imagePath;
        this.reviewToKinds = reviewToKinds;
    }

    public void delete() {
        this.situation = "delete";
    }

    public void update(String content, String time, String imagePath, ReviewToKinds reviewToKinds) {
        this.content = content;
        this.time = time;
        this.imagePath = imagePath;
        this.reviewToKinds = reviewToKinds;
    }

    @Override
    public void uploadImage(String imagePath) {
        this.imagePath = imagePath;
    }
}
