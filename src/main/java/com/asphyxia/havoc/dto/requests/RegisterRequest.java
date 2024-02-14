package com.asphyxia.havoc.dto.requests;

import com.asphyxia.havoc.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank
    private String username;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;

    public Member toMember() {
        return Member.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
    }
}
