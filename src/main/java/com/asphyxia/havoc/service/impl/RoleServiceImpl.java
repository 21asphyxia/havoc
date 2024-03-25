package com.asphyxia.havoc.service.impl;

import com.asphyxia.havoc.domain.Authority;
import com.asphyxia.havoc.domain.Role;
import com.asphyxia.havoc.exception.CustomException;
import com.asphyxia.havoc.repository.RoleRepository;
import com.asphyxia.havoc.service.AuthorityService;
import com.asphyxia.havoc.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final AuthorityService authorityService;
    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role save(Role role) {
        if (findDefaultRole().isPresent() && role.isDefault())
            throw new CustomException("There is already a default role", HttpStatus.UNAUTHORIZED);
        role.setAuthorities(authorityService.getAllByName(role.getAuthorities().stream().map(authority -> authority.getName().toString()).toList()));
        return roleRepository.save(role);

    }

    @Override
    public Optional<Role> findDefaultRole() {
        return roleRepository.findByIsDefaultTrue();
    }

    @Override
    public Role grantAuthorities(List<Authority> authoritiesToGrant, Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new CustomException("Role not found", HttpStatus.NOT_FOUND));
        Set<Authority> newAuthorities = new HashSet<>(role.getAuthorities());
        newAuthorities.addAll(authorityService.getAllByName(authoritiesToGrant.stream().map(authority -> authority.getName().toString()).toList()));
        List<Authority> authorityList = new ArrayList<>(newAuthorities);
        role.setAuthorities(authorityList);
        return roleRepository.save(role);

    }

    @Override
    public Role revokeAuthorities(List<Authority> authoritiesToRevoke, Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new CustomException("Role not found", HttpStatus.NOT_FOUND));
        List<Authority> currentAuthorities = role.getAuthorities();
        currentAuthorities.removeAll(authorityService.getAllByName(authoritiesToRevoke.stream().map(authority -> authority.getName().toString()).toList()));
        role.setAuthorities(currentAuthorities);
        return roleRepository.save(role);
    }

    @Override
    public Role update(Role role, Long id) {
        Role existingRole = getById(id).orElseThrow(() -> new CustomException("Role not found", HttpStatus.NOT_FOUND));
        existingRole.setName(role.getName());
        existingRole.setAuthorities(role.getAuthorities());
        if (role.isDefault() && findDefaultRole().isPresent())
            throw new CustomException("There is already a default role", HttpStatus.UNAUTHORIZED);
        existingRole.setDefault(role.isDefault());
        return roleRepository.save(existingRole);
    }

    @Override
    public Optional<Role> getById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Optional<Role> getByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public void delete(Long id) {
        getById(id).ifPresent(roleRepository::delete);
    }

}
