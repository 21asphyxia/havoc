package com.asphyxia.havoc.service;

import com.asphyxia.havoc.domain.Game;

import java.util.List;

public interface GameService {

    List<Game> getAll();

    Game save(Game game);

    Game update(Game game, Long id);



}
