package com.example.team_project.domain.domain.shop.shop.service.delete;


import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.shop.shop.domain.ShopRepository;
import com.example.team_project.exception.NotPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class ShopDeleteService {

    private final ShopRepository shopRepository;
    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;

    public void delete(Long shopId, String ownerId, String password) {
        Seller seller = sellerRepository.validateSeller(ownerId);

        //비밀번호 체크
        if (!seller.isValidPassword(passwordEncoder, password)) {
            throw new NotPasswordException("비밀번호를 다시 확인해주세요");
        }
        shopRepository.deleteById(shopId);
    }
}
