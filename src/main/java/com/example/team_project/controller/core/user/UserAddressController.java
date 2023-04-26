package com.example.team_project.controller.core.user;

import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
import com.example.team_project.domain.domain.address.service.add.AddressAddService;
import com.example.team_project.domain.domain.address.service.delete.AddressDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/user/address")
public class UserAddressController {

    private final UserAddressRepository userAddressRepository;
    private final AddressDeleteService addressDeleteService;

    @GetMapping
    public String get(@SessionAttribute Long userId, Model model) {

        model.addAttribute("list", userAddressRepository.findByUserId(userId));

        return "thymeleaf/user/address/user-address-list";
    }

    @DeleteMapping("/{addressId}")
    public String delete(@SessionAttribute Long userId, @PathVariable Long addressId) {
        addressDeleteService.delete(userId, addressId);
        return "thymeleaf/user/address/user-address-list";
    }
}
