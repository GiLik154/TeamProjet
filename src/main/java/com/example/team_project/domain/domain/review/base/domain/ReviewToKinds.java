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

    @OneToOne
    private PostReview postReview;

    @OneToOne
    private ProductReview productReview;

    public ReviewToKinds(){}

    public ReviewToKinds(PostReview postReview){
        this.postReview=postReview;
    }
    public ReviewToKinds(ProductReview productReview){
        this.productReview=productReview;
    }
}
