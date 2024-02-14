package com.asphyxia.havoc.dto.responses;

public record AuthenticationResponse(
        String access_token,
        String refresh_token,
        MemberResponse member) {
}
