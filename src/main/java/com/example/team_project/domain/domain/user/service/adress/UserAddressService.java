package com.example.team_project.domain.domain.user.service.adress;

import com.example.team_project.domain.domain.address.domain.UserAddress;

import java.util.List;

public interface UserAddressService {
    void addAddress(String userId, UserAddress userAddress);

    void removeAddress(String userId, UserAddress userAddresss);

    List<UserAddress> getAddressList(String userId);
}
