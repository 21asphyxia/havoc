package com.asphyxia.havoc.service;

import com.asphyxia.havoc.domain.Game;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GameService {

    List<Game> getAll();

    Game save(Game game, MultipartFile image);

    Game update(Game game, Long id);



}
