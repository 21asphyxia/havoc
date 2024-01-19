package com.asphyxia.havoc.dto.responses;

import com.asphyxia.havoc.domain.Member;

public record UserResponse(
        String username,
        String email,
        Integer currency,
        String role
) {
    public static UserResponse fromUser(Member member) {
        return new UserResponse(
                member.getUsername(),
                member.getEmail(),
                member.getCurrency(),
                member.getRole().getName()
        );
    }
}
