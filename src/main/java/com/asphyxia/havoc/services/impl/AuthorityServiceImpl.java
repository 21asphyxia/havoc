package com.asphyxia.havoc.services.impl;

import com.asphyxia.havoc.domain.Authority;
import com.asphyxia.havoc.repositories.AuthorityRepository;
import com.asphyxia.havoc.services.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Override
    public List<Authority> getAllByName(List<String> authorities) {
        List<String> usersAuthorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        if (usersAuthorities.contains("VIEW_AUTHORITIES"))
            return authorityRepository.findAllByName(authorities);
        return null;
    }
}
