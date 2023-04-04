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

    /**
     * 리뷰를 작성한 유저의 정보
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    /**
     * 리뷰의 제목
     */
    private String title;
    /**
     * 리뷰의 내용
     */
    private String content;
    /**
     * 리뷰의 작성시간
     */
    private String time;
    /**
     * 리뷰의 이미지
     */
    private String imagePath;
    /**
     * 리뷰의 존재여부(생성,삭제)등
     */
    private String situation;
    /**
     * 리뷰의 종류(게시물,상품)
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    private ReviewToKinds reviewToKinds;

    protected BaseReview() {
    }

    /**
     * 정보를 입력받아 생성
     */
    public BaseReview(User user, String title,String content, String time, ReviewToKinds reviewToKinds) {
        this.user = user;
        this.title=title;
        this.content = content;
        this.time = time;
        this.situation = "create";
        this.reviewToKinds = reviewToKinds;
    }

    /**
     * 존재여부를 delete 로 변경
     */
    public void delete() {
        this.situation = "delete";
    }

    /**
     * 정보를 입력받아 수정
     */
    public void update(String title,String content, String time,ReviewToKinds reviewToKinds) {
        this.title=title;
        this.content = content;
        this.time = time;
        this.reviewToKinds = reviewToKinds;
    }

    /**
     * 리뷰의 이미지 저장
     */
    @Override
    public void uploadImage(String imagePath) {
        this.imagePath = imagePath;
    }
}
