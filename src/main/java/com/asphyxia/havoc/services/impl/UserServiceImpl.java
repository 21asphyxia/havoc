package com.asphyxia.havoc.services.impl;

import com.asphyxia.havoc.domain.Role;
import com.asphyxia.havoc.domain.User;
import com.asphyxia.havoc.repositories.UserRepository;
import com.asphyxia.havoc.services.UserService;
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
    public List<User> getAll() {
        List<String> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        if (authorities.contains("VIEW_USERS")) return userRepository.findAll();
        return null;
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User assignRole(Long id, Role role) {
        List<String> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        if (authorities.contains("ASSIGN_ROLE_TO_USER")) {
            User user = getById(id).orElse(null);
            if (user != null && role != null) {
                user.setRole(role);
                return userRepository.save(user);
            }
            return null;
        }
        return null;
    }

    @Override
    public User update(User user, Long id) {
        List<String> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        if (authorities.contains("UPDATE_USERS") || SecurityContextHolder.getContext().getAuthentication().getName().equals(user.getEmail())) {
            User existingUser = getById(id).orElse(null);
            if (existingUser != null) {
                existingUser.setEmail(user.getEmail());
                existingUser.setUsername(user.getUsername());
                return userRepository.save(user);
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
