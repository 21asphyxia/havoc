package com.asphyxia.havoc.seeder;

import com.asphyxia.havoc.domain.Authority;
import com.asphyxia.havoc.domain.enums.AuthorityEnum;
import com.asphyxia.havoc.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class AuthoritySeeder {

    private final AuthorityRepository authorityRepository;

    public void seedAuthorities() {
        List<Authority> authorities = new ArrayList<>();
        for (AuthorityEnum authorityName : AuthorityEnum.values()) {
            Authority authority = Authority.builder()
                    .name(authorityName)
                    .build();
            authorities.add(authority);
        }
        authorityRepository.saveAll(authorities);
    }
}
