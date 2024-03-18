package com.asphyxia.havoc.controller;

import com.asphyxia.havoc.domain.Match;
import com.asphyxia.havoc.dto.responses.MatchResponse;
import com.asphyxia.havoc.service.impl.MatchServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchServiceImpl matchService;

    @PostMapping("matchmaking/{game}/join")
    public ResponseEntity<MatchResponse> joinMatchmaking(@PathVariable String game) {
        Match match = matchService.findMatch(game);
        return ResponseEntity.ok(MatchResponse.builder()
                .firstPlayer(match.getFirstPlayer().getEmail())
                .secondPlayer(match.getSecondPlayer() == null ? null : match.getSecondPlayer().getEmail())
                .game(match.getGame().getName())
                .status(match.getSecondPlayer() == null ? "WAITING" : "READY")
                .build());
    }

}
