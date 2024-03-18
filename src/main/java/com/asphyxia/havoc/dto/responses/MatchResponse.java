package com.asphyxia.havoc.dto.responses;

import lombok.Builder;

@Builder
public record MatchResponse(
        String firstPlayer,
        String secondPlayer,
        String game,
        String status) {
}
