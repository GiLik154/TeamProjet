package com.example.team_project.domain.domain.address.service.add;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
import com.example.team_project.domain.domain.address.service.add.dto.AddressAddServiceDto;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressAddServiceImpl implements AddressAddService {
    private final UserAddressRepository userAddressRepository;
    private final UserRepository userRepository;

    /**
     * add를 하기 위해서는 유저의 고유번호와
     * 서비스 dto가 필요함
     * userId로 유저를 가지고 오고
     * dto를 통해 제공받은 정보로 Address를 생성하고 save함.
     * */
    @Override
    public void add(Long userId, AddressAddServiceDto addressAddServiceDto) {
        UserAddress userAddress = new UserAddress(getUser(userId),
                addressAddServiceDto.getName(),
                addressAddServiceDto.getRecipientName(),
                addressAddServiceDto.getPhone(),
                addressAddServiceDto.getStreetAddress(),
                addressAddServiceDto.getDetailedAddress(),
                addressAddServiceDto.getZipCode());

        userAddressRepository.save(userAddress);
    }

    private User getUser(Long userId) {
        return userRepository.validateUserId(userId);
    }
}
