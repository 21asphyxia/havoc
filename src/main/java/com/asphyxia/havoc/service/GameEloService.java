package com.asphyxia.havoc.service;

import com.asphyxia.havoc.domain.Game;
import com.asphyxia.havoc.domain.GameElo;
import com.asphyxia.havoc.domain.MatchResult;
import com.asphyxia.havoc.domain.Member;

import java.util.List;

public interface GameEloService {
    void updateElo(Game game, Member winner, Member loser, MatchResult result);

    List<GameElo> getAllByGame(String game);
}
