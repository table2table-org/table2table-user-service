package com.table2table.userservice.controller;


import com.table2table.userservice.dto.RegisterRequestDto;
import com.table2table.userservice.dto.UserResponseDto;
import com.table2table.userservice.entity.User;
import com.table2table.userservice.repository.UserRepository;
import com.table2table.userservice.service.UserManagementService;
import com.table2table.userservice.util.UserManagementUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@EnableMethodSecurity
public class UserController {

    private final UserManagementService userManagementService;
    private final UserRepository userRepository;

    //persist to db
    @PostMapping("/register")
    public void registerUser(@RequestBody RegisterRequestDto request) {
        userManagementService.registerUser(request);
    }

    // Admin-only: Get all users
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        System.out.println("Authorities: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        List<UserResponseDto> allUsersResponse = UserManagementUtil.convertToDtoList(userManagementService.getAllUsers());
        return ResponseEntity.ok(allUsersResponse);
    }

    // Admin or owner: Get specific user
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.user.credId")
    @GetMapping("getUser/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userManagementService.getUserById(id);

        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserResponseDto userResponse = UserManagementUtil.convertToDto(userOptional.get());
        return ResponseEntity.ok(userResponse);
    }


    @GetMapping("getUserByEmail/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserResponseDto userResponse = UserManagementUtil.convertToDto(userOptional.get());
        return ResponseEntity.ok(userResponse);
    }

    // Admin or owner: Update user
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.user.credId")
    @PutMapping("update/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody User updatedUser
    ) {
        UserResponseDto userResponse = UserManagementUtil.convertToDto(userManagementService.updateUser(id, updatedUser));
        return ResponseEntity.ok(userResponse);
    }

    // Admin-only: Delete user
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        userManagementService.deleteUser(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User deleted successfully");

        return ResponseEntity.ok(response);
    }
}
