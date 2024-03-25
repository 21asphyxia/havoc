package com.asphyxia.havoc.controller;

import com.asphyxia.havoc.domain.GameElo;
import com.asphyxia.havoc.dto.responses.GameEloResponse;
import com.asphyxia.havoc.service.GameEloService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/leaderboards")
@RequiredArgsConstructor
public class LeaderboardController {

    private final GameEloService gameEloService;

    @GetMapping("/{game}")
    public ResponseEntity<List<GameEloResponse>> getAll(@PathVariable String game) {
        List<GameElo> gameElos = gameEloService.getAllByGame(game);
        return ResponseEntity.ok(GameEloResponse.from(gameElos));
    }
}
