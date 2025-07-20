package com.table2table.userservice.service.impl;



import com.table2table.userservice.dto.RegisterRequestDto;
import com.table2table.userservice.dto.UserResponseDto;
import com.table2table.userservice.entity.User;
import com.table2table.userservice.exception.ResourceNotFoundException;
import com.table2table.userservice.repository.UserRepository;
import com.table2table.userservice.service.UserManagementService;
import com.table2table.userservice.util.UserManagementUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {

    private final UserRepository userRepository;

    public void registerUser(RegisterRequestDto request) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .address(request.getAddress())
                .flatNumber(request.getFlatNumber())
                .floor(request.getFloor())
                .phone(request.getPhone())
                .credId(request.getCredId()) // comes from auth-service
                .role(request.getRole())
                .communityId(request.getCommunityId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setPhone(updatedUser.getPhone());
        user.setFlatNumber(updatedUser.getFlatNumber());
        user.setFloor(updatedUser.getFloor());
        user.setRole(updatedUser.getRole());

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

}
