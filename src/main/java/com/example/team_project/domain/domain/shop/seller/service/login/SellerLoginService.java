package com.example.team_project.domain.domain.shop.seller.service.login;


import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerLoginService {

    private final SellerRepository sellerRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    //로그인체크
    public String sellerLogin(String ownerId,String password){
            Seller seller = sellerRepository.validateSeller(ownerId);

        if (!seller.isValidPassword(bCryptPasswordEncoder,password)) {
            throw new BadCredentialsException("Invalid password");
        }

        return seller.getOwnerId();



    }



}
