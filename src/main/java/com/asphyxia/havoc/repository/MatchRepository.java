package com.asphyxia.havoc.repository;

import com.asphyxia.havoc.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    Optional<Match> findByFirstPlayerNotNullAndSecondPlayerIsNullAndGameName(String gameName);

    Optional<Match> findByFirstPlayerEmailLikeAndGameName(String email, String gameName);
}