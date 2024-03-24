package com.asphyxia.havoc.repository;

import com.asphyxia.havoc.domain.Declaration;
import com.asphyxia.havoc.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface DeclarationRepository extends JpaRepository<Declaration, Long> {

    boolean existsByMatch(Match match);
}