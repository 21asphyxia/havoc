package com.asphyxia.havoc.seeder;

import com.asphyxia.havoc.domain.Role;
import com.asphyxia.havoc.repository.AuthorityRepository;
import com.asphyxia.havoc.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            seedRoles();
        }
    }

    public void seedRoles() {
        List<Role> roles = List.of(
                Role.builder()
                        .name("ROLE_USER")
                        .isDefault(true)
                        .build(),
                Role.builder()
                        .name("ROLE_ADMIN")
                        .authorities(authorityRepository.findAll())
                        .isDefault(false)
                        .build()
        );

        roleRepository.saveAll(roles);
    }
}
