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

    /**
     * update를 하기 위해서는 유저의 고유번호와
     * 서비스 dto가 필요함
     * userId와 addressId로 주소 정보를 가지고옴
     * dto를 통해 제공받은 정보로 Address를 수정함
     * */
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
