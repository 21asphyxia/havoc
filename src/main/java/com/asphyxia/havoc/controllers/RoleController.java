package com.asphyxia.havoc.controllers;

import com.asphyxia.havoc.domain.Role;
import com.asphyxia.havoc.dto.requests.RoleRequestDTO;
import com.asphyxia.havoc.dto.responses.RoleResponseDTO;
import com.asphyxia.havoc.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> getAll() {
        List<Role> roles = roleService.getAll();
        if (roles.isEmpty()) return ResponseEntity.badRequest().build();
        else return new ResponseEntity<>(roles.stream().map(RoleResponseDTO::fromRole).toList(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RoleResponseDTO> save(@RequestBody RoleRequestDTO roleToSave) {
        Role role = roleService.save(roleToSave.toRole());
        if (role == null) return ResponseEntity.badRequest().build();
        else return new ResponseEntity<>(RoleResponseDTO.fromRole(role), HttpStatus.OK);
    }

    @PutMapping("/grant_authorities/{id}")
    public ResponseEntity<RoleResponseDTO> grantAuthorities(@RequestBody RoleRequestDTO rolesAuthorities, @PathVariable Long id) {
        Role role = roleService.grantAuthorities(rolesAuthorities.toRole().getAuthorities(), id);
        if (role == null) return ResponseEntity.badRequest().build();
        else return new ResponseEntity<>(RoleResponseDTO.fromRole(role), HttpStatus.OK);
    }

    @PutMapping("/revoke_authorities/{id}")
    public ResponseEntity<RoleResponseDTO> revokeAuthorities(@RequestBody RoleRequestDTO rolesAuthorities, @PathVariable Long id) {
        Role role = roleService.revokeAuthorities(rolesAuthorities.toRole().getAuthorities(), id);
        if (role == null) return ResponseEntity.badRequest().build();
        else return new ResponseEntity<>(RoleResponseDTO.fromRole(role), HttpStatus.OK);
    }

}
