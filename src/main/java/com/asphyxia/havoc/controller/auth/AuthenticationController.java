package com.asphyxia.havoc.controller.auth;

import com.asphyxia.havoc.domain.Member;
import com.asphyxia.havoc.domain.RefreshToken;
import com.asphyxia.havoc.dto.requests.AuthenticationRequest;
import com.asphyxia.havoc.dto.requests.RegisterRequest;
import com.asphyxia.havoc.dto.responses.AuthenticationResponse;
import com.asphyxia.havoc.dto.responses.MessageResponse;
import com.asphyxia.havoc.dto.responses.UserResponse;
import com.asphyxia.havoc.exception.TokenRefreshException;
import com.asphyxia.havoc.security.jwt.JwtUtils;
import com.asphyxia.havoc.security.jwt.service.RefreshTokenService;
import com.asphyxia.havoc.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticate = authenticationService.authenticate(request);
        return ResponseEntity.ok(authenticate);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
        Member member = authenticationService.register(request.toMember());
        return ResponseEntity.ok(UserResponse.fromUser(member));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshtoken(HttpServletRequest request) {
        String refreshToken = jwtUtils.getJwtRefreshFromCookies(request);

        if ((refreshToken != null) && (!refreshToken.isEmpty())) {
            return refreshTokenService.findByToken(refreshToken)
                    .map(refreshTokenService::verifyExpiration)
                    .map(RefreshToken::getUser)
                    .map(user -> {
                        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user);

                        return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                                .body(new MessageResponse("Token is refreshed successfully!"));
                    })
                    .orElseThrow(() -> new TokenRefreshException(refreshToken,
                            "Refresh token is not in database!"));
        }

        return ResponseEntity.badRequest().body(new MessageResponse("Refresh Token is empty!"));
    }
}
