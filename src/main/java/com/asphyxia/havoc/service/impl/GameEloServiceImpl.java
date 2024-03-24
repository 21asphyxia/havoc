package com.asphyxia.havoc.service.impl;

import com.asphyxia.havoc.domain.Game;
import com.asphyxia.havoc.domain.GameElo;
import com.asphyxia.havoc.domain.MatchResult;
import com.asphyxia.havoc.domain.Member;
import com.asphyxia.havoc.repository.GameEloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GameEloServiceImpl {
    private final GameEloRepository gameEloRepository;

    public void updateElo(Game game, Member winner, Member loser, MatchResult result) {
        gameEloRepository.findByGameAndMember(game, winner).ifPresentOrElse(gameElo -> {
            gameElo.setElo(gameElo.getElo() + result.getEloGain());
            gameEloRepository.save(gameElo);
        }, () -> createElo(game, winner, result.getEloGain()));

        gameEloRepository.findByGameAndMember(game, loser).ifPresentOrElse(gameElo -> {
            gameElo.setElo(gameElo.getElo() - result.getEloLoss());
            gameEloRepository.save(gameElo);
        }, () -> createElo(game, loser, 0));

    }

    private void createElo(Game game, Member winner, Integer elo) {
        gameEloRepository.save(GameElo.builder()
                .game(game)
                .member(winner)
                .elo(elo)
                .build());
    }
}
