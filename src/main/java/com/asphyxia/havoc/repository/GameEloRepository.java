package com.asphyxia.havoc.repository;

import com.asphyxia.havoc.domain.Game;
import com.asphyxia.havoc.domain.GameElo;
import com.asphyxia.havoc.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameEloRepository extends JpaRepository<GameElo, Long> {
    Optional<GameElo> findByGameAndMember(Game game, Member member);

    List<GameElo> findAllByGame(Game game);
}
