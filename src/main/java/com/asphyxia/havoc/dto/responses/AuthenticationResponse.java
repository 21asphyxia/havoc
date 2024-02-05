package com.asphyxia.havoc.dto.responses;

public record AuthenticationResponse(
        String token,
        String refreshToken,
        UserResponse user) {
}
