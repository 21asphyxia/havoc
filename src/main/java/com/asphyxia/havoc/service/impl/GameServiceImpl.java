package com.asphyxia.havoc.service.impl;

import com.asphyxia.havoc.domain.Game;
import com.asphyxia.havoc.repository.GameRepository;
import com.asphyxia.havoc.service.GameService;
import com.asphyxia.havoc.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final ImageService imageService;

    @Value("${server.port}")
    private String port;

    @Override
    public List<Game> getAll() {
        List<Game> games = gameRepository.findAll();
        games.forEach(game -> game.setImage("http://localhost:" + port + "/api/v1/images/games/" + game.getImage()));
        return games;
    }

    @Override
    public Game getByName(String name) {
        Game game = gameRepository.findByNameIgnoreCase(name).orElseThrow(() -> new RuntimeException("Game not found"));
        game.setImage("http://localhost:" + port + "/api/v1/images/games/" + game.getImage());
        return game;
    }

    @Override
    public Game save(Game game, MultipartFile image) {
        String dir = "src/main/resources/static/images/games";
        String imageUrl = null;
        try {
            imageUrl = imageService.saveImageToStorage(dir, image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        game.setImage(imageUrl);
        return gameRepository.save(game);
    }

    @Override
    public Game update(Game game, Long id) {
        game.setId(id);
        return gameRepository.save(game);
    }
}
