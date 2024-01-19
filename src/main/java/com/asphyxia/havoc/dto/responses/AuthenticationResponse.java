package com.asphyxia.havoc.dto.responses;

import org.springframework.http.ResponseCookie;

public record AuthenticationResponse(
        ResponseCookie token,
        ResponseCookie refreshToken,
        UserResponse user) {
}
