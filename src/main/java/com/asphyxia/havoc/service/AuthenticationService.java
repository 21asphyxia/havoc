package com.asphyxia.havoc.service;

import com.asphyxia.havoc.dto.requests.AuthenticationRequest;
import com.asphyxia.havoc.dto.requests.RegisterRequest;
import com.asphyxia.havoc.dto.responses.AuthenticationResponse;
import com.asphyxia.havoc.dto.responses.UserResponse;
import org.springframework.stereotype.Component;

@Component
public interface AuthenticationService {

    UserResponse register(RegisterRequest user);

    AuthenticationResponse authenticate(AuthenticationRequest user);

}
