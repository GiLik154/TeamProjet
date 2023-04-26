package com.example.team_project.controller.core.user;

import com.example.team_project.controller.core.user.dto.UserAddressControllerDto;
import com.example.team_project.domain.domain.address.service.add.AddressAddService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/address/update")
public class UserAddressUpdateController {

    private final AddressAddService addressAddService;

    @GetMapping
    public String get() {
        return "thymeleaf/user/address/user-address-add";
    }

    @PostMapping
    public String post(@SessionAttribute Long userId, @Valid UserAddressControllerDto userAddressControllerDto) {
        addressAddService.add(userId, userAddressControllerDto.convertServiceDto());

        return "thymeleaf/user/address/user-address-add";
    }
}
