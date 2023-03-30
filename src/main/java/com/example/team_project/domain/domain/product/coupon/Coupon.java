package com.example.team_project.domain.domain.product.coupon;


import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name="coupon")
@Getter
public class Coupon {
    @Id
    private Long id;

    //쿠폰?
    @Column
    private String name;

    public Coupon(){

    }


}
