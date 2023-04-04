package com.example.team_project.domain.domain.shop.seller.service.join;


import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.shop.seller.service.dto.SellerJoinDto;
import com.example.team_project.exception.PasswordEncodingFailedException;
import com.example.team_project.exception.SellerDuplicateSellerException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerJoinService {
    private final SellerRepository sellerRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    //회원가입
    public void sellerJoin(SellerJoinDto sellerJoinDto){
        //아이디 중복체크
        sellerRepository.duplication(sellerJoinDto.getOwnerId());

        Seller seller = new Seller(
                sellerJoinDto.getOwnerId(),
                sellerJoinDto.getPassword(),
                sellerJoinDto.getOwnerName(),
                sellerJoinDto.getPhoneNumber());

        checkEncodingPw(seller,sellerJoinDto.getPassword());
        sellerRepository.save(seller);
    }

    //password
    private void checkEncodingPw(Seller seller, String password) {
        if (!seller.isEncodePassword(bCryptPasswordEncoder, password)) {
            throw new PasswordEncodingFailedException();
        }
    }
}
