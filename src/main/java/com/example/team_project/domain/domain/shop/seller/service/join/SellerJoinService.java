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
    private final PasswordEncoder passwordEncoder;

    //회원가입
    public void sellerJoin(SellerJoinDto sellerJoinDto) {

        /**
         * seller 에 ownerId가 존재하는지 검색하고 이미 ownerId가 존재한다면,
         * @SellerDuplicateSellerException 예외발생
         * */
        sellerRepository.duplication(sellerJoinDto.getOwnerId(), sellerJoinDto.getPhoneNumber());

        /**
         * 아이디가 존재하지 않으면 값을받아와서 seller 에 저장
         * */
        Seller seller = new Seller(
                sellerJoinDto.getOwnerId(),
                sellerJoinDto.getPassword(),
                sellerJoinDto.getOwnerName(),
                sellerJoinDto.getPhoneNumber());

        checkEncodingPw(seller, sellerJoinDto.getPassword());
        sellerRepository.save(seller);
    }

    /**
     * 비밀번호 암호화
     */
    private void checkEncodingPw(Seller seller, String password) {
        if (!seller.isEncodePassword(passwordEncoder, password)) {
            throw new PasswordEncodingFailedException();
        }
    }
}
