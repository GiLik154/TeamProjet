package com.example.team_project.domain.domain.shop.seller.service.update;

import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.shop.shop.domain.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerUpdateService {

        private final SellerRepository sellerRepository;
        private final PasswordEncoder passwordEncoder;

        //seller: 이름,휴대폰번호     shop:매장번호,주소 수정
        public void sellerUpdate(String sellerId,String password,String ownerName,String phoneNumber){
                Seller seller = sellerRepository.validateSeller(sellerId);

                // 판매자 비밀번호 검증
                if (!seller.isValidPassword(passwordEncoder, password)) {
                        throw new BadCredentialsException("Invalid password");
                }

                seller.update(ownerName,phoneNumber);

        }



}
