package com.example.team_project.domain.domain.shop.seller.service.login;


import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.exception.NotPasswordException;
import com.example.team_project.exception.SellerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.UnknownServiceException;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerLoginService {

    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 로그인 id,pw 체크
     * ownerId,password 를 받아와서 ownerId가 존재하는지 먼저확인후 비밀번호 검증진행 성공시
     *
     * @return seller.getOwnerId
     */
    public String sellerLogin(String ownerId, String password) {

        Seller seller = sellerRepository.validateSeller(ownerId);

        if (!seller.isValidPassword(passwordEncoder, password)) {
            throw new NotPasswordException("비밀번호를 다시 확인해주세요");
        }
        return seller.getOwnerId();
    }
}
