package com.asphyxia.havoc.services;

import com.asphyxia.havoc.domain.Authority;
import com.asphyxia.havoc.domain.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface RoleService {

    Role save(Role role);

    Optional<Role> findDefaultRole();

    Role update(Role role, Long id);

    Optional<Role> getById(Long id);

    Optional<Role> getByName(String name);

    void delete(Long id);

    List<Role> getAll();

    Role grantAuthorities(List<Authority> authorities, Long id);

    Role revokeAuthorities(List<Authority> authorities, Long id);

}
