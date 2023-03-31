package com.example.team_project.domain.domain.address.service.delete;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressDeleteServiceImpl implements AddressDeleteService {
    private final UserAddressRepository userAddressRepository;

    /**
     * delete를 하기 위해서는 유저 고유번호와 주소의 고유번호가 필요함
     * 주소가 있으면 삭제되고, 없으면 그냥 돌아감
     * */
    @Override
    public void delete(Long userId, Long addressId) {
        Optional<UserAddress> optionalAddress = userAddressRepository.findByUserIdAndId(userId, addressId);

        optionalAddress.ifPresent(userAddressRepository::delete);
    }
}
