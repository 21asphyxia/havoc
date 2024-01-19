package com.asphyxia.havoc.dto.responses;

import com.asphyxia.havoc.domain.Authority;
import com.asphyxia.havoc.domain.Role;
import com.asphyxia.havoc.domain.enums.AuthorityEnum;

import java.util.List;

public record RoleResponseDTO(
        String name,
        List<AuthorityEnum> authorities,
        boolean isDefault
) {
    public static RoleResponseDTO fromRole(Role role) {
        return new RoleResponseDTO(
                role.getName(),
                role.getAuthorities().stream().map(Authority::getName).toList(),
                role.isDefault()
        );
    }
}
