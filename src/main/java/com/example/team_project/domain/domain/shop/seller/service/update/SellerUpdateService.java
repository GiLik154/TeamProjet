package com.example.team_project.domain.domain.shop.seller.service.update;

import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.exception.NotPasswordException;
import com.example.team_project.exception.PasswordEncodingFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class SellerUpdateService {

    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;

    //seller: 이름,휴대폰번호     shop:매장번호,주소 수정
    public void sellerUpdate(Long sellerId, String password, String ownerName, String phoneNumber) {
        Seller seller = sellerRepository.validateSellerId(sellerId);

        // 판매자 비밀번호 검증
        if (!seller.isValidPassword(passwordEncoder, password)) {
            throw new NotPasswordException("비밀번호를 다시 확인해주세요");
        }

        //판매자 이름, 판매자 번호 수정
        seller.update(ownerName, phoneNumber);

    }

    public void passwordUpdate(String ownerId, String password) {
        System.out.println(ownerId);
        Seller seller = sellerRepository.validateSeller(ownerId);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        //패스워드변경
        seller.passwordUpdate(encodedPassword);
    }
}
