package com.asphyxia.havoc.dto.responses;

import com.asphyxia.havoc.domain.User;

public record UserResponseDTO(
        String username,
        String email,
        Integer currency,
        String role
) {
    public static UserResponseDTO fromUser(User user) {
        return new UserResponseDTO(
                user.getUsername(),
                user.getEmail(),
                user.getCurrency(),
                user.getRole().getName()
        );
    }
}
