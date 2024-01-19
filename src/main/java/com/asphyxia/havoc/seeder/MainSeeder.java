package com.asphyxia.havoc.seeder;

import com.asphyxia.havoc.repository.AuthorityRepository;
import com.asphyxia.havoc.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MainSeeder implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;


    @Override
    public void run(String... args) {
        if (authorityRepository.count() == 0) {
            new AuthoritySeeder(authorityRepository).seedAuthorities();
        }

        if (roleRepository.count() == 0) {
            new RoleSeeder(roleRepository, authorityRepository).seedRoles();
        }
    }
}
