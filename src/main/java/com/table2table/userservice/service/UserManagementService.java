package com.table2table.userservice.service;



import com.table2table.userservice.dto.RegisterRequestDto;
import com.table2table.userservice.dto.UserResponseDto;
import com.table2table.userservice.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserManagementService {

    void registerUser(RegisterRequestDto request);

    List<User> getAllUsers();                  // For Admin

    Optional<User> getUserById(Long id);

    User updateUser(Long id, User updatedUser);

    void deleteUser(Long id);

}
