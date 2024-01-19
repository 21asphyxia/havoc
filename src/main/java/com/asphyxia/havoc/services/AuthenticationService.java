package com.asphyxia.havoc.services;

import com.asphyxia.havoc.dto.requests.AuthenticationRequest;
import com.asphyxia.havoc.dto.requests.RegisterRequest;
import com.asphyxia.havoc.dto.responses.AuthenticationResponse;
import org.springframework.stereotype.Component;

@Component
public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest user);

    AuthenticationResponse authenticate(AuthenticationRequest user);

}
