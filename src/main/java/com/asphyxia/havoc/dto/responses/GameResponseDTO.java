package com.asphyxia.havoc.dto.responses;

import com.asphyxia.havoc.domain.Game;
import lombok.Builder;

@Builder
public record GameResponseDTO(
        Long id,
        String name,
        String image
) {
    public static GameResponseDTO fromGame(Game game) {
        return GameResponseDTO.builder()
                .id(game.getId())
                .name(game.getName())
                .image(game.getImage())
                .build();
    }
}
