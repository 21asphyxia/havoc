package com.asphyxia.havoc.service.impl;

import com.asphyxia.havoc.domain.Game;
import com.asphyxia.havoc.repository.GameRepository;
import com.asphyxia.havoc.service.GameService;
import com.asphyxia.havoc.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final ImageService imageService;

    @Override
    public List<Game> getAll() {
        return gameRepository.findAll();
    }

    @Override
    public Game getByName(String name) {
        return gameRepository.findByNameIgnoreCase(name).orElseThrow(() -> new RuntimeException("Game not found"));
    }

    public Game getById(Long id) {
        return gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Game not found"));
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
        Game oldGame = getById(id);
        game.setId(id);
        game.setImage(oldGame.getImage());
        return gameRepository.save(game);
    }

    @Override
    public Game update(Game game, Long id, MultipartFile image) {
        game.setId(id);
        String dir = "src/main/resources/static/images/games";
        String imageUrl = null;
        try {
            imageUrl = imageService.saveImageToStorage(dir, image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        game.setImage(imageUrl);

        Game oldGame = getById(id);
        try {
            imageService.deleteImage("src/main/resources/static/images/games", oldGame.getImage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return gameRepository.save(game);
    }

    @Override
    public void delete(Long id) {
        Game game = getById(id);
        try {
            imageService.deleteImage("src/main/resources/static/images/games", game.getImage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        gameRepository.delete(game);
    }
}
