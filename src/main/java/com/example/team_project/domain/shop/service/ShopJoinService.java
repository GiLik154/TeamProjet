package com.example.team_project.domain.shop.service;

import com.example.team_project.domain.shop.domain.Seller;
import com.example.team_project.domain.shop.domain.SellerRepository;
import com.example.team_project.domain.shop.domain.Shop;
import com.example.team_project.domain.shop.domain.ShopRepository;
import com.example.team_project.domain.shop.service.dto.ShopJoinDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class ShopJoinService {

    private final ShopRepository shopRepository;
    private final SellerRepository sellerRepository;

    public void shopJoin(Long sellerId, ShopJoinDto shopJoinDto) {
        Optional<Seller> sellerOptional = sellerRepository.findById(sellerId);
        if (sellerOptional.isPresent()) {
            Shop shop = new Shop(
                    sellerOptional.get(),
                    shopJoinDto.getShopName(),
                    shopJoinDto.getShopAddress(),
                    shopJoinDto.getBusinessRegistrationNumber());
            shopRepository.save(shop);
        }

    }
}
