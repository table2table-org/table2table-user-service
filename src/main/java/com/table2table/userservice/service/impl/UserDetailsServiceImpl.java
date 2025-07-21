package com.table2table.userservice.service.impl;


import com.table2table.security.service.IUserDetailsService;
import com.table2table.userservice.dto.CustomUserDetailsDto;
import com.table2table.userservice.entity.User;
import com.table2table.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements IUserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetailsDto loadUserByUsername(String email) throws UsernameNotFoundException {
        User userCred = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        userCred.setRole("ROLE_"+ userCred.getRole());
        return new CustomUserDetailsDto(userCred); // ✅ injects role like "ADMIN"
    }
}