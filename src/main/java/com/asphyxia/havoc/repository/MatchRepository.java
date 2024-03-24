package com.asphyxia.havoc.repository;

import com.asphyxia.havoc.domain.Game;
import com.asphyxia.havoc.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    Optional<Match> findByFirstPlayerNotNullAndSecondPlayerIsNullAndGame(Game game);

    Optional<Match> findByFirstPlayerEmailLikeAndGame(String email, Game game);

    @Query("SELECT m FROM Match m LEFT JOIN m.secondPlayer sp WHERE (m.firstPlayer.email = :email OR sp.email = :email) AND m.game.name = :gameName AND m.result IS NULL")
    Optional<Match> findByFirstPlayerEmailOrSecondPlayerEmailAndStatusNotFinished(String email, String gameName);
}