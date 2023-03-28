package com.example.team_project.domain.domain.coupons.kinds.domain;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Getter
public class CouponKinds {
    @Id
    private String name;
    @Min(0)
    @Max(100)
    private int discountRate;
}
