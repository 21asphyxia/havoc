package com.asphyxia.havoc.controller;

import com.asphyxia.havoc.domain.Role;
import com.asphyxia.havoc.dto.requests.RoleRequestDTO;
import com.asphyxia.havoc.dto.responses.RoleResponse;
import com.asphyxia.havoc.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_ROLES')")
    public ResponseEntity<List<RoleResponse>> getAll() {
        List<Role> roles = roleService.getAll();
        if (roles.isEmpty()) return ResponseEntity.badRequest().build();
        else return new ResponseEntity<>(roles.stream().map(RoleResponse::fromRole).toList(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    public ResponseEntity<RoleResponse> save(@RequestBody RoleRequestDTO roleToSave) {
        System.out.println();
        Role role = roleService.save(roleToSave.toRole());
        if (role == null) return ResponseEntity.badRequest().build();
        else return new ResponseEntity<>(RoleResponse.fromRole(role), HttpStatus.OK);
    }

    @PutMapping("/grant_authorities/{id}")
    @PreAuthorize("hasAuthority('GRANT_AUTHORITY_TO_ROLE')")
    public ResponseEntity<RoleResponse> grantAuthorities(@RequestBody RoleRequestDTO rolesAuthorities, @PathVariable Long id) {
        Role role = roleService.grantAuthorities(rolesAuthorities.toRole().getAuthorities(), id);
        if (role == null) return ResponseEntity.badRequest().build();
        else return new ResponseEntity<>(RoleResponse.fromRole(role), HttpStatus.OK);
    }

    @PutMapping("/revoke_authorities/{id}")
    @PreAuthorize("hasAuthority('REVOKE_AUTHORITY_FROM_ROLE')")
    public ResponseEntity<RoleResponse> revokeAuthorities(@RequestBody RoleRequestDTO rolesAuthorities, @PathVariable Long id) {
        Role role = roleService.revokeAuthorities(rolesAuthorities.toRole().getAuthorities(), id);
        if (role == null) return ResponseEntity.badRequest().build();
        else return new ResponseEntity<>(RoleResponse.fromRole(role), HttpStatus.OK);
    }

}
