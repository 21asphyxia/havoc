package com.asphyxia.havoc.service;

import com.asphyxia.havoc.domain.Match;

public interface MatchService {
    Match findMatch(String game);

    Match getMatchById(Long id);

    Match updateMatch(Match match);
}
