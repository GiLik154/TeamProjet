package com.example.team_project.domain.domain.review.base.domain;

import com.example.team_project.domain.domain.review.kinds.post.domain.PostReview;
import com.example.team_project.domain.domain.review.kinds.product.domain.ProductReview;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ReviewToKinds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 게시글의 리뷰정보를 저장
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    private PostReview postReview;

    /**
     * 상품의 리뷰정보를 저장
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    private ProductReview productReview;

    public ReviewToKinds(){}

    public ReviewToKinds(PostReview postReview){
        this.postReview=postReview;
    }
    public ReviewToKinds(ProductReview productReview){
        this.productReview=productReview;
    }
}
