package com.asphyxia.havoc.service.impl;

import com.asphyxia.havoc.domain.Member;
import com.asphyxia.havoc.dto.requests.AuthenticationRequest;
import com.asphyxia.havoc.dto.responses.AuthenticationResponse;
import com.asphyxia.havoc.dto.responses.UserResponse;
import com.asphyxia.havoc.repository.UserRepository;
import com.asphyxia.havoc.security.jwt.JwtUtils;
import com.asphyxia.havoc.security.jwt.service.RefreshTokenService;
import com.asphyxia.havoc.security.oauth2.OAuth2UserInfo;
import com.asphyxia.havoc.service.AuthenticationService;
import com.asphyxia.havoc.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;

    @Override
    public Member register(Member member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setRole(roleService.findDefaultRole().orElse(null));
        return userRepository.save(member);
    }

    @Override
    public AuthenticationResponse googleAuthenticate(OAuth2UserInfo info) {
        Member member = userRepository.findByEmail(info.getEmail()).orElseGet(() -> {
            UUID uuid = UUID.randomUUID();
            String encPassword = new BCryptPasswordEncoder().encode(uuid.toString());
            return register(
                    Member.builder()
                            .email(info.getEmail())
                            .password(encPassword)
                            .username(info.getName())
                            .build()
            );

        });

        return authenticate(
                AuthenticationRequest.builder()
                        .email(member.getEmail())
                        .password(member.getPassword())
                        .build()
        );
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Member user = (Member) authentication.getPrincipal();

        String accessToken = jwtUtils.generateTokenFromUsername(user.getEmail());

        String refreshToken = refreshTokenService.createRefreshToken(user.getId()).getToken();

        return new AuthenticationResponse(
                accessToken,
                refreshToken,
                UserResponse.fromUser(user)
        );
    }
}
