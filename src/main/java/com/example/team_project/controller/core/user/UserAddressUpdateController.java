package com.example.team_project.controller.core.user;

import com.example.team_project.controller.core.user.dto.UserAddressAddControllerDto;
import com.example.team_project.controller.core.user.dto.UserAddressUpdateControllerDto;
import com.example.team_project.domain.domain.address.service.add.AddressAddService;
import com.example.team_project.domain.domain.address.service.update.AddressUpdateService;
import com.example.team_project.domain.domain.address.service.update.dto.AddressUpdateServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/address/update")
public class UserAddressUpdateController {

    private final AddressUpdateService addressUpdateService;

    @GetMapping
    public String get() {
        return "thymeleaf/user/address/user-address-update";
    }

    @PostMapping("{addressId}")
    public String post(@SessionAttribute Long userId, @PathVariable Long addressId, @Valid UserAddressUpdateControllerDto userAddressUpdateControllerDto) {
        addressUpdateService.update(userId, addressId, userAddressUpdateControllerDto.convertServiceDto());

        return "redirect:/user/address/list";
    }
}
