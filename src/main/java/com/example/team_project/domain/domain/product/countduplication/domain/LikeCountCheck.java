package com.example.team_project.domain.domain.product.countduplication.domain;


import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Getter
@Builder
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "productId"}))
@AllArgsConstructor
public class LikeCountCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product productId;

    public LikeCountCheck() {
    }
}
