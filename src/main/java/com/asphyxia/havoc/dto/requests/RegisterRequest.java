package com.asphyxia.havoc.dto.requests;

import com.asphyxia.havoc.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String email;
    private String password;

    public Member toMember() {
        return Member.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
    }
}
