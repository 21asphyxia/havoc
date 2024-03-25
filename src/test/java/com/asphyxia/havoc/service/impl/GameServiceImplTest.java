package com.asphyxia.havoc.service.impl;

import com.asphyxia.havoc.domain.Game;
import com.asphyxia.havoc.repository.GameRepository;
import com.asphyxia.havoc.service.ImageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameServiceImplTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private GameServiceImpl gameService;

    @Test
    public void getByInvalidName() {
        when(gameRepository.findByNameIgnoreCase("name")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> gameService.getByName("name"));
    }

    @Test
    public void getByValidName() {
        when(gameRepository.findByNameIgnoreCase("name")).thenReturn(Optional.of(new Game()));
        assertEquals(Game.class, gameService.getByName("name").getClass());
    }

    @Test
    public void addExistingGame() {
        when(gameRepository.existsByNameLikeIgnoreCase("name")).thenReturn(true);
        assertThrows(RuntimeException.class, () -> gameService.save(Game.builder().name("name").build(), null));
    }

    @Test
    public void addNonExistingGame() throws IOException {
        when(gameRepository.existsByNameLikeIgnoreCase("name")).thenReturn(false);
        Game game = Game.builder().name("name").build();
        when(gameRepository.save(game)).thenReturn(game);
        assertEquals(game, gameService.save(game, null));
    }
}