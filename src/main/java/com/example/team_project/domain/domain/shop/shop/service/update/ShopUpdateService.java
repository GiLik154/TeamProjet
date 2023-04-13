package com.example.team_project.domain.domain.shop.shop.service.update;


import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.shop.shop.domain.Shop;
import com.example.team_project.domain.domain.shop.shop.domain.ShopRepository;
import com.example.team_project.exception.NotPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopUpdateService {

    private final SellerRepository sellerRepository;
    private final ShopRepository shopRepository;
    private final PasswordEncoder passwordEncoder;


    //판매처 매장번호,주소 수정
    public void shopUpdate(Long sellerId, Long shopId, String password, String shopName, String shopAddress) {
        Seller seller = sellerRepository.validateSellerId(sellerId);

        // 판매자 ID를 사용하여 저장소에서 상점 엔티티 검색
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new EntityNotFoundException("Shop not found"));
        // 판매자 비밀번호 검증
        if (!seller.isValidPassword(passwordEncoder, password)) {
            throw new NotPasswordException("Invalid password");
        }


        //업데이트
        shop.update(shopName, shopAddress);


    }


}
