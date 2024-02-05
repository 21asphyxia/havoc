package com.asphyxia.havoc.service;

import com.asphyxia.havoc.domain.Member;
import com.asphyxia.havoc.dto.requests.AuthenticationRequest;
import com.asphyxia.havoc.dto.responses.AuthenticationResponse;
import org.springframework.stereotype.Component;

@Component
public interface AuthenticationService {

    Member register(Member member);

    AuthenticationResponse authenticate(AuthenticationRequest user);

    AuthenticationResponse generateRefreshToken(String refreshToken);
}
