package com.asphyxia.havoc.service.impl;

import com.asphyxia.havoc.domain.Game;
import com.asphyxia.havoc.domain.GameElo;
import com.asphyxia.havoc.domain.MatchResult;
import com.asphyxia.havoc.domain.Member;
import com.asphyxia.havoc.repository.GameEloRepository;
import com.asphyxia.havoc.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GameEloServiceImpl {
    private final GameEloRepository gameEloRepository;
    private final GameService gameService;

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

    public List<GameElo> getAllByGame(String game) {
        return gameEloRepository.findAllByGame(gameService.getByName(game));

    }
}
