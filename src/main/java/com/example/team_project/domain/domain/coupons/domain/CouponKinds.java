package com.example.team_project.domain.domain.coupons.domain;

import com.example.team_project.exception.InvalidCouponInfo;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Getter
public class CouponKinds {
    @Id
    private String name;

    private int discountRate;

    private int minPrice;
    private Period period;
    private LocalDate deadline;

    protected CouponKinds() {
    }

    /**
     * validateParam 메소드에서 입력값 검증이 필요함.
     * expirationDate에서 삭제 기한 날짜 설정 가능
     */
    public CouponKinds(String name, int discountRate, int minPrice) {
        validateParam(discountRate, minPrice);
        this.name = name;
        this.discountRate = discountRate;
        this.minPrice = minPrice;
    }

    /**
     * validateParam 메소드에서 입력값 검증이 필요함.
     */
    public void updateApplicableConditions(int discountRate, int minPrice) {
        validateParam(discountRate, minPrice);
        this.discountRate = discountRate;
        this.minPrice = minPrice;
    }

    /**
     * 데드라인을 설정할 수 있음
     * LocalDateTime으로만 받기 때문에 잘못된 값을 넣는 것을 방지함
     */
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    /**
     * 쿠폰의 사용 기간을 설정할 수 있음.
     * Duration통해서 받기 때문에 잘못된 값을 넣는 것을 방지함.
     */
    public void setPeriod(Period period) {
        this.period = period;
    }

    /**
     * 만약 discountRate가 0 미만이면 오류 발생
     * 만약 discountRate가 100 초과면 오류 발생
     * 만약 minPrice가 0 이하면 오류 발생
     */
    private void validateParam(int discountRate, int minPrice) {
        if (discountRate >= 100 || discountRate <= 0 || minPrice <= 0) {
            throw new InvalidCouponInfo();
        }
    }
}
