package com.asphyxia.havoc.controller.auth;

import com.asphyxia.havoc.dto.responses.AuthenticationResponse;
import com.asphyxia.havoc.security.oauth2.GoogleInfo;
import com.asphyxia.havoc.security.oauth2.OAuth2UserInfo;
import com.asphyxia.havoc.service.AuthenticationService;
import com.asphyxia.havoc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class OAuth2Controller {
    private final Log logger = LogFactory.getLog(this.getClass());
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/oauth2/jwt/google")
    public AuthenticationResponse googleLogin(@RequestBody Map<String, Object> data) {
        logger.info("googleLogin");
        logger.info("Google" + data.get("profileObj"));
        OAuth2UserInfo googleInfo = new GoogleInfo((Map<String, Object>) data.get("profileObj"));

        return authenticationService.googleAuthenticate(googleInfo);
    }
}
