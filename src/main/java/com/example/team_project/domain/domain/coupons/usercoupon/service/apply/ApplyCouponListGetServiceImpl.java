package com.example.team_project.domain.domain.coupons.usercoupon.service.apply;

import com.example.team_project.domain.domain.coupons.coupon.service.apply.CouponIsApplicableForPriceAndProductService;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCoupon;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCouponRepository;
import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.enums.CouponStatus;
import com.example.team_project.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplyCouponListGetServiceImpl implements ApplyCouponListGetService {
    private final UserCouponRepository userCouponRepository;
    private final ProductRepository productRepository;
    private final CouponIsApplicableForPriceAndProductService couponIsApplicable;

    @Override
    public List<UserCoupon> getList(Long userId, Long productId, int totalPrice) {
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        ProductCategory productCategory = product.getCategory();

        return userCouponRepository.findByUserIdAndStatusUnused(userId)
                .stream()
                .filter(userCoupon -> userCoupon.getStatus() == CouponStatus.UNUSED &&
                        couponIsApplicable.isApplicableForPriceAndProduct(userCoupon,
                                productCategory,
                                totalPrice))
                .collect(Collectors.toList());
    }
}