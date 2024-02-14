package com.asphyxia.havoc.controller;

import com.asphyxia.havoc.domain.Member;
import com.asphyxia.havoc.domain.Role;
import com.asphyxia.havoc.dto.requests.RoleRequestDTO;
import com.asphyxia.havoc.dto.responses.MemberResponse;
import com.asphyxia.havoc.service.RoleService;
import com.asphyxia.havoc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @PutMapping("/assign_role/{id}")
    public ResponseEntity<MemberResponse> assignRole(@RequestBody RoleRequestDTO request, @PathVariable Long id) {
        Role role = roleService.getByName(request.name()).orElse(null);
        Member member = userService.assignRole(id, role);
        if (member == null || role == null) return ResponseEntity.badRequest().build();
        else return new ResponseEntity<>(MemberResponse.fromMember(member), HttpStatus.OK);
    }

}
