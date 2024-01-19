package com.asphyxia.havoc.controllers;

import com.asphyxia.havoc.domain.Role;
import com.asphyxia.havoc.domain.User;
import com.asphyxia.havoc.dto.requests.RoleRequestDTO;
import com.asphyxia.havoc.dto.responses.UserResponseDTO;
import com.asphyxia.havoc.services.RoleService;
import com.asphyxia.havoc.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @PutMapping("/assign_role/{id}")
    public ResponseEntity<UserResponseDTO> assignRole(@RequestBody RoleRequestDTO request, @PathVariable Long id) {
        Role role = roleService.getByName(request.name()).orElse(null);
        User user = userService.assignRole(id, role);
        if (user == null && role == null) return ResponseEntity.badRequest().build();
        else return new ResponseEntity<>(UserResponseDTO.fromUser(user), HttpStatus.OK);
    }

}
