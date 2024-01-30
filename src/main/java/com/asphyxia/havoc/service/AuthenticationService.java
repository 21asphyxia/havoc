package com.asphyxia.havoc.service;

import com.asphyxia.havoc.domain.Member;
import com.asphyxia.havoc.dto.requests.AuthenticationRequest;
import com.asphyxia.havoc.dto.responses.AuthenticationResponse;
import com.asphyxia.havoc.security.oauth2.OAuth2UserInfo;
import org.springframework.stereotype.Component;

@Component
public interface AuthenticationService {

    Member register(Member member);

    AuthenticationResponse googleAuthenticate(OAuth2UserInfo info);

    AuthenticationResponse authenticate(AuthenticationRequest user);

}
