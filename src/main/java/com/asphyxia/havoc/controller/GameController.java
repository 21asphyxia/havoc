package com.asphyxia.havoc.controller;

import com.asphyxia.havoc.domain.Game;
import com.asphyxia.havoc.dto.requests.GameRequestDTO;
import com.asphyxia.havoc.dto.responses.GameResponseDTO;
import com.asphyxia.havoc.dto.responses.MessageResponse;
import com.asphyxia.havoc.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @Value("${server.port}")
    private String port;

    @GetMapping
    public ResponseEntity<List<GameResponseDTO>> getAll() {
        List<Game> games = gameService.getAll();
        games.forEach(game -> game.setImage("http://localhost:" + port + "/api/v1/images/games/" + game.getImage()));
        return new ResponseEntity<>(games.stream().map(GameResponseDTO::fromGame).toList(), HttpStatus.OK);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('CREATE_GAME')")
    public ResponseEntity<GameResponseDTO> save(GameRequestDTO gameToSave) {
        Game game = gameService.save(gameToSave.toGame(), gameToSave.image());
        game.setImage("http://localhost:" + port + "/api/v1/images/games/" + game.getImage());
        return new ResponseEntity<>(GameResponseDTO.fromGame(game), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('UPDATE_GAME')")
    public ResponseEntity<GameResponseDTO> update(@PathVariable Long id, GameRequestDTO gameToUpdate) {
        Game game;
        if (gameToUpdate.image() == null) {
            game = gameService.update(gameToUpdate.toGame(), id);
            game.setImage("http://localhost:" + port + "/api/v1/images/games/" + game.getImage());
        } else {
            game = gameService.update(gameToUpdate.toGame(), id, gameToUpdate.image());
            game.setImage("http://localhost:" + port + "/api/v1/images/games/" + game.getImage());
        }
        return new ResponseEntity<>(GameResponseDTO.fromGame(game), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_GAME')")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        gameService.delete(id);
        return new ResponseEntity<>(new MessageResponse("Game deleted successfully"), HttpStatus.NO_CONTENT);
    }
}
