package com.asphyxia.havoc.dto.requests;

import com.asphyxia.havoc.domain.Authority;
import com.asphyxia.havoc.domain.Game;
import com.asphyxia.havoc.domain.Role;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
