package com.example.team_project.domain.domain.shop.seller.service;


import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.shop.seller.service.dto.SellerJoinDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerJoinService {


    private final SellerRepository sellerRepository;

    public void sellerJoin(SellerJoinDto sellerJoinDto){
        Seller seller = new Seller(
                sellerJoinDto.getOwnerId(),
                sellerJoinDto.getPassword(),
                sellerJoinDto.getOwnerName(),
                sellerJoinDto.getPhoneNumber());

        sellerRepository.save(seller);

    }







}
