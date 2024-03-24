package com.asphyxia.havoc.controller;

import com.asphyxia.havoc.domain.Game;
import com.asphyxia.havoc.domain.GameElo;
import com.asphyxia.havoc.dto.requests.GameRequestDTO;
import com.asphyxia.havoc.dto.responses.GameEloResponse;
import com.asphyxia.havoc.dto.responses.GameResponseDTO;
import com.asphyxia.havoc.dto.responses.MessageResponse;
import com.asphyxia.havoc.service.GameService;
import com.asphyxia.havoc.service.impl.GameEloServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/leaderboards")
@RequiredArgsConstructor
public class LeaderboardController {

    private final GameEloServiceImpl gameEloService;

    @GetMapping("/{game}")
    public ResponseEntity<List<GameEloResponse>> getAll(@PathVariable String game) {
        List<GameElo> gameElos = gameEloService.getAllByGame(game);
        return ResponseEntity.ok(GameEloResponse.from(gameElos));
    }
}
