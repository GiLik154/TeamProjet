package com.example.team_project.domain.domain.address.service.update;

import com.example.team_project.domain.domain.address.service.update.dto.AddressUpdateServiceDto;

public interface AddressUpdateService {
    void update(Long userId, Long addressId, AddressUpdateServiceDto addressUpdateServiceDto);
}
