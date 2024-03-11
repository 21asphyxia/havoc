package com.asphyxia.havoc.controller;

import com.asphyxia.havoc.domain.Game;
import com.asphyxia.havoc.dto.requests.GameRequestDTO;
import com.asphyxia.havoc.dto.responses.GameResponseDTO;
import com.asphyxia.havoc.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping
    public ResponseEntity<List<GameResponseDTO>> getAll() {
        List<Game> games = gameService.getAll();
        return new ResponseEntity<>(games.stream().map(GameResponseDTO::fromGame).toList(), HttpStatus.OK);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<GameResponseDTO> save(GameRequestDTO gameToSave) {
        Game game = gameService.save(gameToSave.toGame(), gameToSave.image()[0]);
        return new ResponseEntity<>(GameResponseDTO.fromGame(game), HttpStatus.OK);
    }
}
