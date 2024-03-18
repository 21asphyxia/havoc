package com.asphyxia.havoc.service.impl;

import com.asphyxia.havoc.domain.Game;
import com.asphyxia.havoc.domain.Match;
import com.asphyxia.havoc.domain.Member;
import com.asphyxia.havoc.repository.MatchRepository;
import com.asphyxia.havoc.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl {

    private final MatchRepository matchRepository;

    private final GameService gameService;

    public Match findMatch(String game) {
        Member loggedInMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findOrCreateMatch(loggedInMember, game);
    }

    private Match findOrCreateMatch(Member loggedInMember, String game) {
        return matchRepository.findByFirstPlayerEmailLikeAndGameName(loggedInMember.getEmail(), game)
                .orElseGet(() -> createMatchIfNoneExists(loggedInMember, game));
    }

    private Match createMatchIfNoneExists(Member loggedInMember, String game) {
        matchRepository.findByFirstPlayerNotNullAndSecondPlayerIsNullAndGameName(game)
                .ifPresent(m -> joinMatch(loggedInMember, m));

        return matchRepository.findByFirstPlayerEmailLikeAndGameName(loggedInMember.getEmail(), game)
                .orElseGet(() -> createNewMatch(loggedInMember, game));
    }

    private void joinMatch(Member loggedInMember, Match m) {
        m.setSecondPlayer(loggedInMember);
        matchRepository.save(m);
    }

    private Match createNewMatch(Member loggedInMember, String game) {
        Game foundGame = gameService.getByName(game);
        Match newMatch = Match.builder()
                .date(LocalDate.now())
                .firstPlayer(loggedInMember)
                .game(foundGame)
                .build();
        matchRepository.save(newMatch);
        return newMatch;
    }
}
