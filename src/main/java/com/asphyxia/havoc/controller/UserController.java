package com.asphyxia.havoc.controller;

import com.asphyxia.havoc.domain.Member;
import com.asphyxia.havoc.domain.Role;
import com.asphyxia.havoc.dto.requests.RoleRequestDTO;
import com.asphyxia.havoc.dto.responses.UserResponse;
import com.asphyxia.havoc.service.RoleService;
import com.asphyxia.havoc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

    @PutMapping("/assign_role/{id}")
    public ResponseEntity<UserResponse> assignRole(@RequestBody RoleRequestDTO request, @PathVariable Long id) {
        Role role = roleService.getByName(request.name()).orElse(null);
        Member member = userService.assignRole(id, role);
        if (member == null || role == null) return ResponseEntity.badRequest().build();
        else return new ResponseEntity<>(UserResponse.fromUser(member), HttpStatus.OK);
    }

}