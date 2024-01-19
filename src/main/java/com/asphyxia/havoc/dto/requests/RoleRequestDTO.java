package com.asphyxia.havoc.dto.requests;

import com.asphyxia.havoc.domain.Authority;
import com.asphyxia.havoc.domain.Role;

import java.util.List;

public record RoleRequestDTO(
        String name,
        List<Authority> authorities,
        boolean isDefault
) {
    public Role toRole() {
        return Role.builder()
                .name(name)
                .isDefault(isDefault)
                .authorities(authorities)
                .build();
    }
}
