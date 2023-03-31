package com.example.team_project.domain.domain.address.service.update;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
import com.example.team_project.domain.domain.address.service.update.dto.AddressUpdateServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressUpdateServiceImpl implements AddressUpdateService {
    private final UserAddressRepository userAddressRepository;

    @Override
    public void update(Long userId, Long addressId, AddressUpdateServiceDto addressUpdateServiceDto) {
        Optional<UserAddress> optionalAddress = userAddressRepository.findByUserIdAndId(userId, addressId);

        optionalAddress.ifPresent(userAddress ->
                userAddress.updateInfo(addressUpdateServiceDto.getName(),
                addressUpdateServiceDto.getRecipientName(),
                addressUpdateServiceDto.getPhone(),
                addressUpdateServiceDto.getStreetAddress(),
                addressUpdateServiceDto.getDetailedAddress(),
                addressUpdateServiceDto.getZipCode()));
    }
}
