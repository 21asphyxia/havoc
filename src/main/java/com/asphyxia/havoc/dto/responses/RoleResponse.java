package com.asphyxia.havoc.dto.responses;

import com.asphyxia.havoc.domain.Authority;
import com.asphyxia.havoc.domain.Role;
import com.asphyxia.havoc.domain.enums.AuthorityEnum;

import java.util.List;

public record RoleResponse(
        String name,
        List<AuthorityEnum> authorities,
        boolean isDefault
) {
    public static RoleResponse fromRole(Role role) {
        return new RoleResponse(
                role.getName(),
                role.getAuthorities().stream().map(Authority::getName).toList(),
                role.isDefault()
        );
    }
}
