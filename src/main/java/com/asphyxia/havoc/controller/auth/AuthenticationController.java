package com.asphyxia.havoc.controller.auth;

import com.asphyxia.havoc.domain.Member;
import com.asphyxia.havoc.dto.requests.AuthenticationRequest;
import com.asphyxia.havoc.dto.requests.RefreshRequest;
import com.asphyxia.havoc.dto.requests.RegisterRequest;
import com.asphyxia.havoc.dto.responses.AuthenticationResponse;
import com.asphyxia.havoc.dto.responses.MemberResponse;
import com.asphyxia.havoc.service.AuthenticationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        AuthenticationResponse authenticate = authenticationService.authenticate(request);
        return ResponseEntity.ok(authenticate);
    }

    @PostMapping("/register")
    public ResponseEntity<MemberResponse> register(@RequestBody @Valid RegisterRequest request) {
        Member member = authenticationService.register(request.toMember());
        return ResponseEntity.ok(MemberResponse.fromMember(member));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody @Valid RefreshRequest refreshToken) {
        return ResponseEntity.ok(authenticationService.generateRefreshToken(refreshToken.getRefreshToken()));
    }
}

