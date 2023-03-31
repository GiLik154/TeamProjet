package com.example.team_project.domain.domain.address.service.add;

import com.example.team_project.domain.domain.address.service.add.dto.AddressAddServiceDto;

public interface AddressAddService {
    void add(Long userId, AddressAddServiceDto addressAddServiceDto);
}
