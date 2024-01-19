package com.asphyxia.havoc.service.impl;

import com.asphyxia.havoc.domain.Member;
import com.asphyxia.havoc.domain.Role;
import com.asphyxia.havoc.repository.UserRepository;
import com.asphyxia.havoc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<Member> getAll() {
        List<String> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        if (authorities.contains("VIEW_USERS")) return userRepository.findAll();
        return null;
    }

    @Override
    public Optional<Member> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Member assignRole(Long id, Role role) {
        List<String> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        if (authorities.contains("ASSIGN_ROLE_TO_USER")) {
            Member member = getById(id).orElse(null);
            if (member != null && role != null) {
                member.setRole(role);
                return userRepository.save(member);
            }
            return null;
        }
        return null;
    }

    @Override
    public Member update(Member member, Long id) {
        List<String> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        if (authorities.contains("UPDATE_USERS") || SecurityContextHolder.getContext().getAuthentication().getName().equals(member.getEmail())) {
            Member existingMember = getById(id).orElse(null);
            if (existingMember != null) {
                existingMember.setEmail(member.getEmail());
                existingMember.setUsername(member.getUsername());
                return userRepository.save(member);
            }
            return null;
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        List<String> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        if (authorities.contains("DELETE_USERS") || SecurityContextHolder.getContext().getAuthentication().getName().equals(getById(id).get().getEmail()))
            getById(id).ifPresent(userRepository::delete);
    }
}
