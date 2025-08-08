package com.table2table.userservice.util;



import com.table2table.userservice.dto.UserResponseDto;
import com.table2table.userservice.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserManagementUtil {

    public static UserResponseDto convertToDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getUserId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setFlatNumber(user.getFlatNumber());
        dto.setFloor(user.getFloor());
        dto.setRole(user.getRole());
        dto.setCredId(user.getCredId());
        return dto;
    }

    public static List<UserResponseDto> convertToDtoList(List<User> users) {
        return users.stream()
                .map(UserManagementUtil::convertToDto)
                .collect(Collectors.toList());
    }

}
