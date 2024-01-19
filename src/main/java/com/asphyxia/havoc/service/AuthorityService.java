package com.asphyxia.havoc.service;

import com.asphyxia.havoc.domain.Authority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AuthorityService {
    List<Authority> getAllByName(List<String> authorities);
}
