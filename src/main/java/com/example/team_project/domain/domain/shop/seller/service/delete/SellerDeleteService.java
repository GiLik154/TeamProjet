package com.example.team_project.domain.domain.shop.seller.service.delete;


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
public class SellerDeleteService {

    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;

    //회원탈퇴 selelr 탈퇴시 shop도 같이 삭제
    public void delete(Long sellerId, String password, String ownerId) {
        Seller seller = sellerRepository.validateSeller(ownerId);

        if (!seller.isValidPassword(passwordEncoder, password)) {
            throw new BadCredentialsException("Invalid password");
        }

        sellerRepository.deleteById(sellerId);
    }

}
