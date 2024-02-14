package com.asphyxia.havoc.dto.responses;

import com.asphyxia.havoc.domain.Member;

public record MemberResponse(
        String username,
        String email,
        Integer currency,
        String role
) {
    public static MemberResponse fromMember(Member member) {
        return new MemberResponse(
                member.getUsername(),
                member.getEmail(),
                member.getCurrency(),
                member.getRole().getName()
        );
    }
}
