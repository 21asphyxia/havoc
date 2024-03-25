package com.asphyxia.havoc.dto.requests;

import com.asphyxia.havoc.domain.Game;
import org.springframework.web.multipart.MultipartFile;

public record GameRequestDTO(
        String name,
        MultipartFile image
) {
    public Game toGame() {
        return Game.builder()
                .name(name)
                .build();
    }
}
