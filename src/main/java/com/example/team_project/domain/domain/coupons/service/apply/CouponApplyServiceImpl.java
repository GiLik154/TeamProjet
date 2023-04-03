package com.example.team_project.domain.domain.coupons.service.apply;

import com.example.team_project.domain.domain.coupons.domain.*;
import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.exception.NotApplyCouponException;
import com.example.team_project.exception.NotFoundCouponException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CouponApplyServiceImpl implements CouponApplyService {
    private final UserHaveCouponRepository userHaveCouponRepository;
    private final CouponInCategoryRepository couponInCategoryRepository;

    /**
     * 가격에 쿠폰을 적용할 수 있는지 확인.
     * 받는 매개변수는 유저 아이디, 쿠폰 아이디, 오더(주문)
     * 유저아이디와 쿠폰 아이디로 UserHaveCoupon을 찾은 후에
     * 그 쿠폰의 정규화 되어있는 CouponKinds를 찾아서 조건을 찾아옴. (할인율, 최저가격)
     */
    @Override
    public int apply(Long userId, Long couponId, Order order) {
        CouponKinds coupon = getCoupon(userId, couponId).getCouponKinds();

        if (!applyAble(coupon, order)) {
            throw new NotApplyCouponException();
        }

        return order.getOrderToProduct().getTotalPrice() * (100 - coupon.getDiscountRate()) / 100;
    }

    private UserHaveCoupon getCoupon(Long userId, Long couponId) {
        return userHaveCouponRepository.findByUserIdAndId(userId, couponId).orElseThrow(NotFoundCouponException::new);
    }

    /**
     * 적용이 가능한지 boolean으로 체크함.
     * (조건 2가지, 카테고리 + 최저가격)
     */
    private boolean applyAble(CouponKinds coupon, Order order) {
        return isApplicableToCategory(coupon, order) &&
                isMinimumPriceSatisfied(order, coupon.getMinPrice());
    }

    /**
     * CouponKinds와 구매한 오더의 카테고리가 같은지 체크.
     * (쿠폰 카테고리는 여러개가 있을 수 있으로 List로 받아와서 Strem으로 비교)
     */
    private boolean isApplicableToCategory(CouponKinds coupon, Order order) {
        List<CouponInCategory> categories = couponInCategoryRepository.findByCouponKindsName(coupon.getName());

        return categories.stream().
                anyMatch(category -> category.getProductCategory().equals(order.getOrderToProduct().getProduct().getCategory()));
    }

    /**
     * CouponKinds와 구매한 오더의 가격 비교
     */
    private boolean isMinimumPriceSatisfied(Order order, int couponMinPrice) {
        return order.getTotalPrice() >= couponMinPrice;
    }
}