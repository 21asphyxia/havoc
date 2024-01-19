package com.asphyxia.havoc.service.impl;

import com.asphyxia.havoc.domain.Authority;
import com.asphyxia.havoc.domain.enums.AuthorityEnum;
import com.asphyxia.havoc.repository.AuthorityRepository;
import com.asphyxia.havoc.service.AuthorityService;
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
        if (usersAuthorities.contains("VIEW_AUTHORITIES")) {
            List<AuthorityEnum> authorityEnums = authorities.stream().map(AuthorityEnum::valueOf).toList();
            return authorityRepository.findAllByNameIn(authorities);
        }
        return null;
    }
}
