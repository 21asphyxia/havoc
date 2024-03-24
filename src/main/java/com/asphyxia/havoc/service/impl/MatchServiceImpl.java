package com.asphyxia.havoc.service.impl;

import com.asphyxia.havoc.domain.Game;
import com.asphyxia.havoc.domain.Match;
import com.asphyxia.havoc.domain.Member;
import com.asphyxia.havoc.repository.MatchRepository;
import com.asphyxia.havoc.service.GameService;
import com.asphyxia.havoc.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl {

    private final MatchRepository matchRepository;
    private final GameService gameService;

    private final ImageService imageService;

    public Match findMatch(String game) {
        Game foundGame = gameService.getByName(game);
        Member loggedInMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findOrCreateMatch(loggedInMember, foundGame);
    }

    private Match findOrCreateMatch(Member loggedInMember, Game game) {
        return matchRepository.findByFirstPlayerEmailOrSecondPlayerEmailAndStatusNotFinished(loggedInMember.getEmail(), game.getName())
                .orElseGet(() -> createMatchIfNoneExists(loggedInMember, game));
    }

    private Match createMatchIfNoneExists(Member loggedInMember, Game game) {
        matchRepository.findByFirstPlayerNotNullAndSecondPlayerIsNullAndGame(game)
                .ifPresent(m -> joinMatch(loggedInMember, m));

        return matchRepository.findByFirstPlayerEmailOrSecondPlayerEmailAndStatusNotFinished(loggedInMember.getEmail(), game.getName())
                .orElseGet(() -> createNewMatch(loggedInMember, game));
    }

    private void joinMatch(Member loggedInMember, Match m) {
        m.setSecondPlayer(loggedInMember);
        matchRepository.save(m);
    }

    private Match createNewMatch(Member loggedInMember, Game game) {
        Match newMatch = Match.builder()
                .dateTime(LocalDateTime.now())
                .firstPlayer(loggedInMember)
                .game(game)
                .build();
        matchRepository.save(newMatch);
        return newMatch;
    }

    public Match getMatchById(Long id) {
        return matchRepository.findById(id).orElseThrow(() -> new RuntimeException("Match not found"));
    }

    public Match updateMatch(Match match) {
        return matchRepository.save(match);
    }
}
