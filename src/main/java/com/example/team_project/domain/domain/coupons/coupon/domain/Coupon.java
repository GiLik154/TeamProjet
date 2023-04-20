package com.example.team_project.domain.domain.coupons.coupon.domain;

import com.example.team_project.exception.InvalidCouponInfo;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

/**
 * 쿠폰의 종류 (UserHaveCoupon과 CouponKinds에서 사용됨)
 */
@Entity
@Getter
public class Coupon {
    /**
     * String으로 직접 넣어줘야 함.
     * 중복되는 쿠폰의 이름은 사용 불가능
     */
    @Id
    private String name;
    /**
     * 할인율. (%로 사용됨) - ex) int 5 의 경우 -> 5%
     */
    private int discountRate;
    /**
     * 최소 가격
     */
    private int minPrice;
    /**
     * 유저가 가질 수 있는 쿠폰의 최대 갯수
     */
    private int maxCouponCount;
    /**
     * 쿠폰의 적용 기간
     * (유저가 발급 받은 후 며칠까지 사용이 가능한지)
     * null인 경우 적용 기간 없음
     */
    private Period period;
    /**
     * 데드라인 (이 쿠폰의 종류가 언제 만료되는지)
     * null일 경우 데드라인 없음.
     */
    private LocalDate deadline = LocalDate.of(2999, 12, 31);

    protected Coupon() {
    }

    /**
     * validateParam 메소드에서 입력값 검증이 필요함.
     * expirationDate에서 삭제 기한 날짜 설정 가능
     */
    public Coupon(String name, int discountRate, int minPrice, int maxCouponCount) {
        validateDiscountRateAndMinPrice(discountRate, minPrice);
        this.name = name;
        this.discountRate = discountRate;
        this.minPrice = minPrice;
        this.maxCouponCount = maxCouponCount;
    }

    /**
     * validateParam 메소드에서 입력값 검증이 필요함.
     */
    public void updateDiscountRateAndMinPrice(int discountRate, int minPrice, int maxCouponCount) {
        validateDiscountRateAndMinPrice(discountRate, minPrice);
        this.discountRate = discountRate;
        this.minPrice = minPrice;
        this.maxCouponCount = maxCouponCount;
    }

    /**
     * 파라미터를 검증하는 메소드 (비율과 최저가격을 사용할 수 있는지 검증)
     * 만약 discountRate가 0 미만이면 오류 발생
     * 만약 discountRate가 100 초과면 오류 발생
     * 만약 minPrice가 0 이하면 오류 발생
     */
    private void validateDiscountRateAndMinPrice(int discountRate, int minPrice) {
        if (discountRate >= 100 || discountRate <= 0 || minPrice <= 0) {
            throw new InvalidCouponInfo();
        }
    }

    /**
     * 데드라인을 설정할 수 있음
     * LocalDateTime으로만 받기 때문에 잘못된 값을 넣는 것을 방지함
     */
    public void updateDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    /**
     * 쿠폰의 사용 기간을 설정할 수 있음.
     * Duration통해서 받기 때문에 잘못된 값을 넣는 것을 방지함.
     */
    public void updatePeriod(Period period) {
        this.period = period;
    }
}
