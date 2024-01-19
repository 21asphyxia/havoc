package com.asphyxia.havoc.repository;

import com.asphyxia.havoc.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    List<Authority> findAllByNameIn(Collection<String> name);
}
