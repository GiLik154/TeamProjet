package com.example.team_project.security;

import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.exception.UserNotFoundException;
import com.example.team_project.security.dto.UserSecurityDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {


        Optional<User> result = userRepository.getWithRoles(userId);

        if (result.isEmpty()) {
            throw new UsernameNotFoundException("username not found");
        }

        User user = result.get();

        UserSecurityDTO userSecurityDTO =
                new UserSecurityDTO(
                        user.getUserId(),
                        user.getPassword(),
                        user.getUserName(),
                        user.getEmail(),
                        user.getPhoneNumber(),
                        user.getUserGrade(),
                        user.isDel(),
                        false,
                        user.getRoleSet()
                                .stream().map(userRole -> new SimpleGrantedAuthority("ROLE_" + userRole.name()))
                                .collect(Collectors.toList())
                );

        return userSecurityDTO;
    }
}
