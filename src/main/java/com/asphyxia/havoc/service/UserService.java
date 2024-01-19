package com.asphyxia.havoc.service;

import com.asphyxia.havoc.domain.Member;
import com.asphyxia.havoc.domain.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface UserService {

    List<Member> getAll();

    Optional<Member> getById(Long id);

    Member assignRole(Long id, Role role);

    Member update(Member member, Long id);

    void delete(Long id);

}
