package com.asphyxia.havoc.dto.responses;

import com.asphyxia.havoc.domain.GameElo;
import lombok.Builder;

import java.util.List;

@Builder
public record GameEloResponse(
        String username,
        Integer elo
) {
    public static GameEloResponse from(GameElo gameElo) {
        return GameEloResponse.builder()
                .username(gameElo.getMember().getUsername())
                .elo(gameElo.getElo())
                .build();
    }

    public static List<GameEloResponse> from(List<GameElo> gameElos) {
        return gameElos.stream()
                .map(GameEloResponse::from)
                .toList();
    }
}
